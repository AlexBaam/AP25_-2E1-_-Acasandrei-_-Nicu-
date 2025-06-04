package org.example;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TestRunner {

    // Statistici
    private static int totalClassesScanned = 0;
    private static int totalTestClassesFound = 0;
    private static int totalTestMethodsFound = 0;
    private static int totalTestsExecuted = 0;
    private static int totalTestsPassed = 0;
    private static int totalTestsFailed = 0;
    private static Set<String> processedClasses = new HashSet<>(); // pt a nu procesa duplicate a claselor din JAR/folder

    // directorul -> clasele compilate din fisiere .java
    private static final File COMPILED_CLASSES_DIR = new File("compiled_classes");

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Utilizare: java -cp <classpath> org.example.TestRunner <cale_fisier_sau_director>");
            System.out.println("Exemple: ");
            System.out.println("  java -cp . org.example.TestRunner src/main/java/org/example/MyClass2.java");
            System.out.println("  java -cp . org.example.TestRunner src/main/java");
            System.out.println("  java -cp . org.example.TestRunner target/myproject.jar");
            return;
        }

        String inputPath = args[0];
        File inputFile = new File(inputPath);

        List<URL> urlsToLoad = new ArrayList<>();
        List<String> classNamesToProcess = new ArrayList<>();

        if (!COMPILED_CLASSES_DIR.exists()) {
            COMPILED_CLASSES_DIR.mkdirs();
        }

        try {
            // la classpath ClassLoader punem directorul de compilare
            urlsToLoad.add(COMPILED_CLASSES_DIR.toURI().toURL());

            urlsToLoad.add(new File(".").toURI().toURL());

            if (inputFile.isFile()) {
                if (inputPath.toLowerCase().endsWith(".java")) {
                    System.out.println(">>> Compilare fisier Java: " + inputPath);
                    boolean success = compileJavaFile(inputFile, COMPILED_CLASSES_DIR);
                    if (!success) {
                        System.err.println("Compilare esuata pentru " + inputPath + ". Abort.");
                        return;
                    }
                    String fullClassName = getClassNameFromFile(inputFile);
                    if (fullClassName != null) {
                        classNamesToProcess.add(fullClassName);
                    } else {
                        System.err.println("Nu s-a putut determina numele clasei dupa compilare pentru: " + inputPath);
                        return;
                    }

                } else if (inputPath.toLowerCase().endsWith(".class")) {
                    // .class -> numele complet + adaugam calea parinte in URL
                    String normalizedPath = inputPath.replace(File.separatorChar, '/');
                    int dotClassIndex = normalizedPath.toLowerCase().indexOf(".class");
                    if (dotClassIndex != -1) {
                        String fullClassName = normalizedPath.substring(0, dotClassIndex).replace('/', '.');
                        classNamesToProcess.add(fullClassName);

                        // punem direct. radacina la URL
                        Path classPath = Paths.get(inputPath);
                        Path rootPath = null;

                        //cauta, radacina pachetului
                        if (classPath.getParent() != null) {
                            urlsToLoad.add(classPath.getParent().toUri().toURL());
                        }

                    } else {
                        System.err.println("Fisier .class invalid: " + inputPath);
                        return;
                    }

                } else if (inputPath.toLowerCase().endsWith(".jar")) {
                    urlsToLoad.add(inputFile.toURI().toURL());
                    extractClassNamesFromJar(inputFile, classNamesToProcess);
                } else {
                    System.err.println("Fisier nevalid. Trebuie sa fie .java, .class sau .jar.");
                    return;
                }
            } else if (inputFile.isDirectory()) {
                System.out.println(">>> Scanare si compilare director: " + inputPath);
                // o luam recursiv prin director -> compilam .java + cautam .class
                // punem direct. de input la URL -> incarcam clasele compilate
                urlsToLoad.add(inputFile.toURI().toURL());
                findJavaAndClassFilesRecursively(inputFile, "", classNamesToProcess, COMPILED_CLASSES_DIR);
            } else {
                System.err.println("Calea specificata nu este un fisier sau un director valid.");
                return;
            }

            // o sa o folosim pt a incarca clasele pt analizam + rukam testele
            URLClassLoader customClassLoader = new URLClassLoader(urlsToLoad.toArray(new URL[0]), ClassLoader.getSystemClassLoader());

            System.out.println("\n--- Analiza si Rularea Testelor ---");

            for (String className : classNamesToProcess) {
                if (processedClasses.contains(className)) {
                    continue;
                }
                try {
                    totalClassesScanned++;

                    Class<?> targetClass = customClassLoader.loadClass(className);

                    if (Modifier.isPublic(targetClass.getModifiers())) {
                        System.out.println("\n----------------------------------------");
                        System.out.println("Analizand clasa: " + targetClass.getName());

                        //neneram + afisam prototip complet
                        System.out.println("\nPrototype (javap style):");
                        System.out.println(PrototypeGenerator.generatePrototype(targetClass));

                        // analiza bytecode + instrumentare (ASM)  !!! -> pt bonus
                        System.out.println("\n--- Analiza Bytecode si Instrumentare (ASM) ---");
                        try {
                            // luam bytecode clasei
                            byte[] originalBytes = BytecodeProcessor.getClassBytes(customClassLoader, className);
                            if (originalBytes != null) {
                                System.out.println("  Bytecode original pentru " + className + " (primele 20 octeti): " + Arrays.toString(Arrays.copyOf(originalBytes, Math.min(originalBytes.length, 20))));

                                // luam o metoda
                                byte[] instrumentedBytes = BytecodeProcessor.instrumentClass(originalBytes, "staticTestNoArgs");
                                if (instrumentedBytes != null) {
                                    System.out.println("  Bytecode instrumentat pentru " + className + " (primele 20 octeti): " + Arrays.toString(Arrays.copyOf(instrumentedBytes, Math.min(instrumentedBytes.length, 20))));

                                    // mai facem un CutomClassLoader ca sa incarcam versiunea
                                    CustomClassLoader instrumentationClassLoader = new CustomClassLoader(urlsToLoad.toArray(new URL[0]));
                                    Class<?> instrumentedClass = instrumentationClassLoader.defineClassFromBytes(className, instrumentedBytes);
                                    System.out.println("  Clasa instrumentata '" + instrumentedClass.getName() + "' a fost reincarcata cu succes.");

                                    // rulam testele
                                    System.out.println("\n  Rularea testelor pe clasa INSTRUMENTATA:");
                                    runTestsInClass(instrumentedClass);
                                } else {
                                    System.out.println("  Nu s-a putut instrumenta clasa " + className + ". Instrumentarea a fost ignorata.");
                                }
                            } else {
                                System.out.println("  Nu s-a putut extrage bytecode-ul pentru " + className + ". Analiza bytecode ignorata.");
                            }

                            // generare dinamica a-> clase
                            String dynamicClassName = "org.example.DynamicGeneratedClass";
                            System.out.println("\n  Generare dinamica a clasei: " + dynamicClassName + "...");
                            Class<?> dynamicClass = BytecodeProcessor.generateDynamicClass(dynamicClassName);
                            if (dynamicClass != null) {
                                System.out.println("  Clasa dinamica '" + dynamicClass.getName() + "' a fost generata si incarcata cu succes.");
                                // apelam sayhello
                                try {
                                    Method sayHelloMethod = dynamicClass.getMethod("sayHello");
                                    sayHelloMethod.invoke(null); // metoda statica
                                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                    System.err.println("Eroare la apelarea metodei 'sayHello' din clasa dinamica: " + e.getMessage());
                                }
                            }

                        } catch (Exception bytecodeEx) {
                            System.err.println("Eroare majora la procesarea bytecode-ului pentru " + className + ": " + bytecodeEx.getMessage());
                            bytecodeEx.printStackTrace();
                        }
                        System.out.println("--- Sfarsitul Analizei Bytecode ---");

                        // rulam testele -> clasa originala (daca nu e dea rulata ->  instrumentata)
                        System.out.println("\n  Rularea testelor pe clasa ORIGINALA (daca nu au fost rulate deja pe cea instrumentata sau ca demonstratie):");
                        if (targetClass.isAnnotationPresent(Test.class)) {
                            totalTestClassesFound++;
                            System.out.println("Clasa '" + targetClass.getName() + "' este adnotata cu @Test.");
                            runTestsInClass(targetClass);
                        } else {
                            System.out.println("Clasa '" + targetClass.getName() + "' nu este adnotata cu @Test. Verificam doar metodele individuale @Test.");
                            runTestsInClass(targetClass);
                        }
                    } else {
                        System.out.println("\nClasa '" + targetClass.getName() + "' este non-publica si va fi ignorata pentru testare directa.");
                    }
                    processedClasses.add(className); // marcam clasa ca procesata

                } catch (ClassNotFoundException e) {
                    System.err.println("Eroare: Clasa '" + className + "' nu a fost gasita de classloader. " + e.getMessage());
                } catch (NoClassDefFoundError e) {
                    System.err.println("Eroare la incarcarea clasei '" + className + "': dependinta lipsa. " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("A aparut o eroare neasteptata la procesarea clasei " + className + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }

            System.out.println("\n----------------------------------------");
            System.out.println("--- Statistici Generale Teste ---");
            System.out.println("  Clase scanate: " + totalClassesScanned);
            System.out.println("  Clase @Test gasite: " + totalTestClassesFound);
            System.out.println("  Metode @Test gasite: " + totalTestMethodsFound);
            System.out.println("  Teste executate: " + totalTestsExecuted);
            System.out.println("  Teste reusite: " + totalTestsPassed);
            System.out.println("  Teste esuate: " + totalTestsFailed);
            System.out.println("----------------------------------------");

        } catch (MalformedURLException e) {
            System.err.println("Eroare: URL-ul caii specificate este malformat. " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Eroare I/O la citirea fisierului/directorului: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("A aparut o eroare generala: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // pt compilare fisier java
    private static boolean compileJavaFile(File javaFile, File outputDirectory) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("Eroare: Nu s-a gasit JavaCompiler.");
            return false;
        }

        // warninguri + erori
        DiagnosticCollector<javax.tools.JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, StandardCharsets.UTF_8);

        Iterable<? extends javax.tools.JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(javaFile));

        List<String> options = new ArrayList<>();
        options.add("-d"); // directorul de output pt fisiere .class
        options.add(outputDirectory.getAbsolutePath());
        // adaugam la classpath + directorul target/classes pt dependente
        options.add("-classpath");
        options.add(new File("target/classes").getAbsolutePath());

        /// task de compilare
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);

        boolean success = task.call();

        if (!success) {
            System.err.println("Erori de compilare pentru " + javaFile.getName() + ":");
            for (Diagnostic<? extends javax.tools.JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.err.println("  " + diagnostic.getKind() + ": " + diagnostic.getMessage(null) +
                        " (Linia: " + diagnostic.getLineNumber() + ", Coloana: " + diagnostic.getColumnNumber() + ")");
            }
        }

        try {
            fileManager.close();
        } catch (IOException e) {
            System.err.println("Eroare la inchiderea file manager: " + e.getMessage());
        }
        return success;
    }

    // pt a determina numele complep al clasei dintr un .java
    private static String getClassNameFromFile(File javaFile) {
        try {
            // citim -> luam numele pachetului
            String content = Files.readString(javaFile.toPath());
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("package\\s+([a-zA-Z_][a-zA-Z0-9_\\.]*);").matcher(content);
            String packageName = "";
            if (matcher.find()) {
                packageName = matcher.group(1);
            }
            String className = javaFile.getName().replace(".java", "");
            if (!packageName.isEmpty()) {
                return packageName + "." + className;
            }
            return className;
        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului Java pentru a determina numele clasei: " + e.getMessage());
            return null;
        }
    }


    /// recursiv prin foldere pt a cauta .class + .java
    private static void findJavaAndClassFilesRecursively(File directory, String packageName, List<String> classNames, File compiledOutputDirectory) {
        if (!directory.isDirectory()) {
            return;
        }
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                String newPackageName = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                findJavaAndClassFilesRecursively(file, newPackageName, classNames, compiledOutputDirectory);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName.isEmpty() ?
                        file.getName().replace(".class", "") :
                        packageName + "." + file.getName().replace(".class", "");
                classNames.add(className);
            } else if (file.getName().endsWith(".java")) {
                System.out.println("  Detectat fisier Java pentru compilare: " + file.getAbsolutePath());
                boolean success = compileJavaFile(file, compiledOutputDirectory);
                if (success) {
                    // adaugam clasa compilata la lista de procesat
                    String className = packageName.isEmpty() ?
                            file.getName().replace(".java", "") :
                            packageName + "." + file.getName().replace(".java", "");
                    classNames.add(className);
                } else {
                    System.err.println("  Compilare esuata pentru " + file.getName() + ". Clasa nu va fi procesata.");
                }
            }
        }
    }

    // extrage numele claselor -> fisier JAR
    private static void extractClassNamesFromJar(File jarFile, List<String> classNames) throws IOException {
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace("/", ".").replace(".class", "");
                    classNames.add(className);
                }
            }
        }
    }

    // ruleaza testele
    private static void runTestsInClass(Class<?> targetClass) {
        System.out.println("\n  Executare metode @Test in clasa " + targetClass.getName() + ":");
        Object instance = null;

        // ! abstracta / interfata -> cream o instanta
        if (!Modifier.isAbstract(targetClass.getModifiers()) && !targetClass.isInterface() && !targetClass.isEnum() && !targetClass.isAnnotation()) {
            try {
                // cautam constructor fara arg
                Constructor<?> constructor = targetClass.getDeclaredConstructor();
                constructor.setAccessible(true); // avem acces chiar daca e privat
                instance = constructor.newInstance();
                System.out.println("    Instanta a clasei '" + targetClass.getName() + "' creata cu succes pentru testele non-statice.");
            } catch (NoSuchMethodException e) {
                System.out.println("    Atentie: Clasa '" + targetClass.getName() + "' nu are un constructor fara argumente. Metodele non-statice @Test NU vor fi executate.");
            } catch (Exception e) {
                System.err.println("    Eroare la crearea instantei pentru clasa " + targetClass.getName() + ": " + e.getMessage());
            }
        }

        for (Method method : targetClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                totalTestMethodsFound++;
                System.out.println("    Tentativa de executie: " + method.getName() + "()");
                totalTestsExecuted++;

                method.setAccessible(true); // avem acces chiar daca e privat
                try {
                    if (Modifier.isStatic(method.getModifiers())) { // Verificam daca metoda este statica
                        Object[] mockArgs = generateMockArguments(method.getParameterTypes());
                        method.invoke(null, mockArgs); // Static -> instanta este null
                        totalTestsPassed++;
                    } else {
                        // Non-static -> avem nevoie de o instanta
                        if (instance != null) {
                            Object[] mockArgs = generateMockArguments(method.getParameterTypes());
                            method.invoke(instance, mockArgs); //apelam instanta
                            totalTestsPassed++;
                        } else {
                            System.out.println("      IGNORAT: Metoda '" + method.getName() + "' este non-statica, dar nu s-a putut crea o instanta a clasei sau clasa este abstracta/interfata.");
                            totalTestsFailed++;
                        }
                    }
                } catch (InvocationTargetException e) {
                    totalTestsFailed++;
                    System.err.println("      FAILED: Testul '" + method.getName() + "' a esuat cu exceptia: " + e.getCause().getClass().getName() + ": " + e.getCause().getMessage());
                } catch (IllegalArgumentException e) {
                    totalTestsFailed++;
                    System.err.println("      FAILED: Testul '" + method.getName() + "' a esuat din cauza argumentelor incorecte: " + e.getMessage());
                } catch (Exception e) {
                    totalTestsFailed++;
                    System.err.println("      FAILED: Testul '" + method.getName() + "' a esuat cu o eroare neasteptata: " + e.getClass().getName() + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    // generam valori mock pt argumentele metodelor
    private static Object[] generateMockArguments(Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];
            if (type == int.class || type == Integer.class) {
                args[i] = 42;
            } else if (type == String.class) {
                args[i] = "mock_string_" + (i + 1);
            } else if (type == boolean.class || type == Boolean.class) {
                args[i] = true;
            } else if (type == double.class || type == Double.class) {
                args[i] = 3.14;
            } else if (type == float.class || type == Float.class) {
                args[i] = 1.618f;
            } else if (type == long.class || type == Long.class) {
                args[i] = 12345L;
            } else if (type == short.class || type == Short.class) {
                args[i] = (short) 10;
            } else if (type == byte.class || type == Byte.class) {
                args[i] = (byte) 1;
            } else if (type == char.class || type == Character.class) {
                args[i] = 'X';
            } else {
                args[i] = null;
                System.out.println("    Atentie: Tip de argument '" + type.getName() + "' nu este suportat pentru generare mock. Se foloseste null.");
            }
        }
        return args;
    }
}


/*
    mvn exec:exec
 */

