package org.example;

// javac -cp "lib/javassist-3.13.0-GA.jar" src/main/java/org/example/*.java
// java -cp "src/main/java;lib/javassist-3.13.0-GA.jar;target/compiled-classes" org.example.TestRunner src/main/java/org/example/MyClass2.java
// java -cp "src/main/java;lib/javassist-3.13.0-GA.jar;target/compiled-classes" org.example.TestRunner src/main/java/org/example/


import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TestRunner {

    private static int totalClassesScanned = 0;
    private static int totalTestClasses = 0; // Clase cu @Test la nivel de clasă
    private static int totalTestMethods = 0; // Metode @Test
    private static int testsExecuted = 0;
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    // Director temporar pentru clasele compilate din fisiere .java
    private static final String COMPILED_CLASSES_DIR = "target/compiled-classes";

    public static void main(String[] args) {

        // 1. Validarea argumentelor la pornire
        if (args.length == 0) {
            System.out.println("Utilizare: java -cp <classpath> org.example.TestRunner <cale_fisier_sau_director>");
            System.out.println("Exemple:");
            System.out.println("  Pentru .java: java -cp \"src/main/java;lib/javassist-3.13.0-GA.jar;target/compiled-classes\" org.example.TestRunner src/main/java/org/example/MyClass2.java");
            System.out.println("  Pentru director: java -cp \"src/main/java;lib/javassist-3.13.0-GA.jar;target/compiled-classes\" org.example.TestRunner src/main/java/org/example/");
            System.out.println("  Pentru .class: java -cp . org.example.TestRunner org/example/MyClass.class");
            System.out.println("  Pentru .jar: java -cp \"mytests.jar\" org.example.TestRunner mytests.jar"); // Sau java -cp .;mytests.jar org.example.TestRunner mytests.jar
            return;
        }

        String inputPath = args[0];
        File inputFile = new File(inputPath);

        List<URL> urlsToLoad = new ArrayList<>();
        List<String> classNamesToProcess = new ArrayList<>();
        Set<String> processedClasses = new HashSet<>(); // Pentru a evita procesarea duplicatelor

        try {
            // Asigura-te ca directorul de output pentru compilare exista
            File compiledDir = new File(COMPILED_CLASSES_DIR);
            if (!compiledDir.exists()) {
                compiledDir.mkdirs();
            }

            if (inputFile.isFile()) {
                if (inputPath.toLowerCase().endsWith(".java")) {
                    System.out.println("Input este un fisier .java. Incerc sa compilez...");
                    List<File> javaFiles = Arrays.asList(inputFile); // inputFile este deja calea absolută din contextul B12
                    boolean compiled = JavaSourceCompiler.compile(javaFiles, compiledDir);
                    if (!compiled) {
                        System.err.println("Eroare la compilarea fisierului .java. Abort.");
                        return;
                    }
                    urlsToLoad.add(compiledDir.toURI().toURL());
                    // Extragem numele clasei complet calificat (org.example.MyClass2)
                    String relativePath = inputPath.substring(inputPath.indexOf("org" + File.separator + "example")).replace(File.separatorChar, '.');
                    String className = relativePath.replace(".java", "");
                    classNamesToProcess.add(className);

                    // APLICAM INSTRUMENTAREA DUPA COMPILARE
                    System.out.println("Aplicam instrumentare bytecode cu Javassist...");
                    for (String clsName : classNamesToProcess) {
                        try {
                            // Asigura-te ca instrumentezi doar clasele tale, nu si altele din sistem
                            if (clsName.startsWith("org.example.")) {
                                BytecodeInstrumentor.instrumentClass(clsName, compiledDir.getAbsolutePath());
                            } else {
                                System.out.println("  Clasa " + clsName + " nu apartine pachetului org.example, sar instrumentarea.");
                            }
                        } catch (javassist.NotFoundException | javassist.CannotCompileException e) {
                            System.err.println("Eroare la instrumentarea clasei " + clsName + ": " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                } else if (inputPath.toLowerCase().endsWith(".class")) {
                    urlsToLoad.add(inputFile.getParentFile().toURI().toURL());
                    String relativePath = inputPath.substring(inputPath.indexOf("org" + File.separator + "example")).replace(File.separatorChar, '.');
                    String className = relativePath.replace(".class", "");
                    classNamesToProcess.add(className);
                } else if (inputPath.toLowerCase().endsWith(".jar")) {
                    urlsToLoad.add(inputFile.toURI().toURL());
                    extractClassNamesFromJar(inputFile, classNamesToProcess); // Aceasta metoda trebuie implementata
                } else {
                    System.err.println("Fisier nevalid. Trebuie sa fie .java, .class sau .jar.");
                    return;
                }
            } else if (inputFile.isDirectory()) {
                // Daca inputul e un director, putem cauta si .java si .class
                // Mai intai compilam toate .java-urile din el
                List<File> javaFiles = new ArrayList<>();
                findSourceFilesRecursively(inputFile, javaFiles); // Găsește .java din directorul dat

                if (!javaFiles.isEmpty()) {
                    System.out.println("Directorul contine fisiere .java. Incerc sa compilez...");
                    boolean compiled = JavaSourceCompiler.compile(javaFiles, compiledDir);
                    if (!compiled) {
                        System.err.println("Eroare la compilarea fisierelor .java din director. Abort.");
                        return;
                    }
                    // Adaugam directorul cu clasele compilate la URL-uri
                    urlsToLoad.add(compiledDir.toURI().toURL());
                }

                // Apoi adaugam directorul original (pentru .class-uri existente)
                urlsToLoad.add(inputFile.toURI().toURL());

                // Acum gasim toate .class-urile (din compilare si existente)
                findClassNamesInDirectory(compiledDir, "", classNamesToProcess); // Clasele compilate
                findClassNamesInDirectory(inputFile, "", classNamesToProcess); // Clasele deja existente

                // APLICAM INSTRUMENTAREA DUPA COMPILARE (pe toate clasele compilate)
                System.out.println("Aplicam instrumentare bytecode cu Javassist pe clasele compilate...");
                for (String clsName : new ArrayList<>(classNamesToProcess)) {
                    try {
                        // Instrumenteaza doar clasele din pachetul tau specific
                        if (clsName.startsWith("org.example.")) {
                            BytecodeInstrumentor.instrumentClass(clsName, compiledDir.getAbsolutePath());
                        } else {
                            System.out.println("  Clasa " + clsName + " nu apartine pachetului org.example, sar instrumentarea.");
                        }
                    } catch (javassist.NotFoundException | javassist.CannotCompileException e) {
                        System.err.println("Eroare la instrumentarea clasei " + clsName + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }

            } else {
                System.err.println("Calea specificata nu este un fisier sau un director valid.");
                return;
            }

            // 2. Incarca clasele cu un ClassLoader personalizat
            URLClassLoader customClassLoader = new URLClassLoader(urlsToLoad.toArray(new URL[0]), ClassLoader.getSystemClassLoader());

            System.out.println("\n--- Incepere Teste ---");

            // 3. Proceseaza fiecare clasa
            for (String className : classNamesToProcess) {
                if (!processedClasses.add(className)) {
                    continue; // Clasa a fost deja procesata
                }

                totalClassesScanned++;
                System.out.println("\nProcesare clasa: " + className);

                try {
                    Class<?> clazz = customClassLoader.loadClass(className);

                    // Genereaza si afiseaza prototipul clasei
                    System.out.println("  Prototipul clasei:\n" + PrototypeGenerator.generatePrototype(clazz));

                    // Verifica dacă clasa este adnotată cu @Test (la nivel de clasă)
                    if (clazz.isAnnotationPresent(Test.class)) {
                        totalTestClasses++;
                        System.out.println("  Clasa " + className + " este o clasa de test (@Test class).");
                        runTestsInClass(clazz);
                    } else {
                        System.out.println("  Clasa " + className + " nu este o clasa de test (@Test class), se cauta metode @Test.");
                        runTestsInClass(clazz); // Ruleaza metodele @Test chiar daca clasa nu e adnotata
                    }

                } catch (ClassNotFoundException e) {
                    System.err.println("Eroare: Clasa " + className + " nu a putut fi gasita. " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Eroare la procesarea clasei " + className + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException | IOException e) {
            System.err.println("Eroare: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("A aparut o eroare generala: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 4. Afiseaza statisticile finale
            System.out.println("\n--- Sumar Teste ---");
            System.out.println("Clase scanate: " + totalClassesScanned);
            System.out.println("Clase de test (@Test): " + totalTestClasses);
            System.out.println("Metode de test (@Test) gasite: " + totalTestMethods);
            System.out.println("Teste executate: " + testsExecuted);
            System.out.println("Teste reusite: " + testsPassed);
            System.out.println("Teste esuate: " + testsFailed);

            // Curata directorul temporar dupa rulare (optional)
            try {
                deleteDirectory(new File(COMPILED_CLASSES_DIR));
            } catch (IOException e) {
                System.err.println("Eroare la curatarea directorului temporar: " + e.getMessage());
            }
        }
    }

    // --- METODE AJUTATOARE ---

    private static void runTestsInClass(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                totalTestMethods++;
                System.out.println("\n  Rulare test: " + clazz.getSimpleName() + "." + method.getName() + "()");
                testsExecuted++;

                try {
                    // Genereaza argumente mock pentru metoda, daca este necesar
                    Object[] args = generateMockArguments(method.getParameterTypes());

                    if (Modifier.isStatic(method.getModifiers())) {
                        // Dacă metoda este statică, apelăm direct
                        method.invoke(null, args);
                    } else {
                        // Dacă metoda nu este statică, avem nevoie de o instanță a clasei
                        try {
                            // Încercăm să obținem un constructor fără argumente
                            Constructor<?> constructor = clazz.getDeclaredConstructor();
                            constructor.setAccessible(true); // Accesăm și constructorii privați
                            Object instance = constructor.newInstance();
                            method.invoke(instance, args);
                        } catch (NoSuchMethodException e) {
                            System.err.println("    Eroare: Clasa " + clazz.getName() + " nu are un constructor fara argumente pentru a apela metoda non-statica. Test esuat.");
                            testsFailed++;
                            continue; // Trecem la următorul test
                        } catch (InvocationTargetException e) {
                            // Prindem excepțiile aruncate de metoda de test
                            System.err.println("    Test esuat: " + clazz.getSimpleName() + "." + method.getName() + "() - " + e.getTargetException().getMessage());
                            testsFailed++;
                            continue;
                        }
                    }
                    testsPassed++;
                    System.out.println("    Test reusit: " + clazz.getSimpleName() + "." + method.getName() + "()");

                } catch (InvocationTargetException e) {
                    // Prindem excepțiile aruncate de metoda de test
                    System.err.println("    Test esuat: " + clazz.getSimpleName() + "." + method.getName() + "() - " + e.getTargetException().getMessage());
                    testsFailed++;
                } catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
                    System.err.println("    Eroare la executarea testului " + clazz.getSimpleName() + "." + method.getName() + "(): " + e.getMessage());
                    testsFailed++;
                }
            }
        }
    }

    private static Object[] generateMockArguments(Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> paramType = parameterTypes[i];
            if (paramType == int.class || paramType == Integer.class) {
                args[i] = 42; // Valoare mock pentru int
            } else if (paramType == String.class) {
                args[i] = "mock_string"; // Valoare mock pentru String
            } else if (paramType == boolean.class || paramType == Boolean.class) {
                args[i] = true;
            } else if (paramType == double.class || paramType == Double.class) {
                args[i] = 1.23;
            } else if (paramType == long.class || paramType == Long.class) {
                args[i] = 12345L;
            } else if (paramType == float.class || paramType == Float.class) {
                args[i] = 4.56f;
            } else if (paramType == char.class || paramType == Character.class) {
                args[i] = 'X';
            } else if (paramType == byte.class || paramType == Byte.class) {
                args[i] = (byte) 1;
            } else if (paramType == short.class || paramType == Short.class) {
                args[i] = (short) 2;
            }
            else {
                System.err.println("  Atentie: Tip de parametru " + paramType.getName() + " nu este gestionat pentru mocking. Se foloseste null.");
                args[i] = null;
            }
        }
        return args;
    }

    private static void findSourceFilesRecursively(File directory, List<File> javaFiles) {
        if (!directory.isDirectory()) {
            return;
        }
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                findSourceFilesRecursively(file, javaFiles);
            } else if (file.getName().endsWith(".java")) {
                javaFiles.add(file);
            }
        }
    }

    private static void findClassNamesInDirectory(File directory, String packageName, List<String> classNames) {
        if (!directory.isDirectory()) {
            return;
        }
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                String newPackageName = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                findClassNamesInDirectory(file, newPackageName, classNames);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName.isEmpty() ?
                        file.getName().replace(".class", "") :
                        packageName + "." + file.getName().replace(".class", "");
                if (!classNames.contains(className)) {
                    classNames.add(className);
                }
            }
        }
    }

    private static void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            File[] allContents = directory.listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    deleteDirectory(file);
                }
            }
            Files.delete(directory.toPath());
            System.out.println("Director sters: " + directory.getAbsolutePath());
        }
    }

    private static void extractClassNamesFromJar(File inputFile, List<String> classNamesToProcess) {
        try (JarFile jarFile = new JarFile(inputFile)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class") && !entry.isDirectory()) {
                    String className = entry.getName()
                            .replace(".class", "")
                            .replace('/', '.');
                    classNamesToProcess.add(className);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului JAR: " + e.getMessage());
        }
    }
}