package org.example;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

//  cd src/main/java
//  javac org/example/*.java
//  java -cp . org.example.TestRunner .
public class TestRunner {

    // Statistici
    private static int totalClassesScanned = 0;
    private static int totalTestClassesFound = 0;
    private static int totalTestMethodsFound = 0;
    private static int totalTestsExecuted = 0;
    private static int totalTestsPassed = 0;
    private static int totalTestsFailed = 0;
    private static Set<String> processedClasses = new HashSet<>(); // pt a nu procesa duplicate a claselor din JAR/folder

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Hopa! Nu e bine!");
            return;
        }

        String inputPath = args[0];
        File inputFile = new File(inputPath);

        List<URL> urlsToLoad = new ArrayList<>();
        List<String> classNamesToProcess = new ArrayList<>();

        try {
            // URLClassLoader primeste calea radacina a claselor

            if (inputFile.isFile()) {
                if (inputPath.toLowerCase().endsWith(".class")) {

                    File classFile = new File(inputPath);
                    String fullClassName = null;

                    File currentDir = new File("."); // directorul curent de rulare
                    urlsToLoad.add(currentDir.toURI().toURL()); // adauga directorul de compilare la classpath

                    // construim numele complet al clasei -> nornind de la radacina
                    // ex: sa zicem ca inputPath = "org/example/MyClass2.class" -> numele complet e "org.example.MyClass2"
                    String normalizedPath = inputPath.replace(File.separatorChar, '/');
                    int dotClassIndex = normalizedPath.toLowerCase().indexOf(".class");
                    if (dotClassIndex != -1) {
                        // luam partea cu numele clasei si a pachetului
                        fullClassName = normalizedPath.substring(0, dotClassIndex).replace('/', '.');
                    } else {
                        System.err.println("Fisier .class invalid: " + inputPath);
                        return;
                    }
                    if (fullClassName != null) {
                        classNamesToProcess.add(fullClassName);
                    } else {
                        System.err.println("Nu s-a putut determina numele clasei din calea: " + inputPath);
                        return;
                    }

                } else if (inputPath.toLowerCase().endsWith(".jar")) {
                    urlsToLoad.add(inputFile.toURI().toURL()); // adauga JAR direct
                    extractClassNamesFromJar(inputFile, classNamesToProcess);
                } else {
                    System.err.println("Fisier nevalid. Trebuie sa fie .class sau .jar.");
                    return;
                }
            } else if (inputFile.isDirectory()) {
                // ex: daca input = "org/example/" sau "src/main/java/" -> adaugam URL care reprezinta radacina de unde incep pachetele
                urlsToLoad.add(inputFile.toURI().toURL()); // adauga directorul
                urlsToLoad.add(inputFile.toURI().toURL());
                findClassFilesRecursively(inputFile, "", classNamesToProcess);
            } else {
                System.err.println("Calea specificata nu este un fisier sau un director valid.");
                return;
            }
            // ClassLoader personalizat cu URL-urile determinate -> stie sa incarce clasele din aceste URL
            URLClassLoader customClassLoader = new URLClassLoader(urlsToLoad.toArray(new URL[0]), ClassLoader.getSystemClassLoader());

            System.out.println("--- Analiza si Rularea Testelor ---");

            for (String className : classNamesToProcess) {
                if (processedClasses.contains(className)) {
                    continue; // clasa deja procesata
                }
                try {
                    totalClassesScanned++;
                    // incarcam clasa -> ClassLoader personalizat
                    Class<?> targetClass = customClassLoader.loadClass(className);

                    // vedem daca clasa e publica
                    if (Modifier.isPublic(targetClass.getModifiers())) {
                        System.out.println("\n----------------------------------------");
                        System.out.println("Analizand clasa: " + targetClass.getName());

                        // generare + afisare prototip complet
                        System.out.println("\nPrototype (javap style):");
                        System.out.println(PrototypeGenerator.generatePrototype(targetClass));

                        // identifica si invoca metodele @Test (~ suntem vrajitori ~)
                        if (targetClass.isAnnotationPresent(Test.class)) {
                            totalTestClassesFound++;
                            System.out.println("Clasa '" + targetClass.getName() + "' este adnotata cu @Test.");
                            runTestsInClass(targetClass);
                        } else {
                            System.out.println("Clasa '" + targetClass.getName() + "' nu este adnotata cu @Test. Verificam doar metodele individuale @Test.");
                            // ATENTIE!! chiar daca clasa nu e adnotata -> metodele pot fi !!!
                            runTestsInClass(targetClass);
                        }
                    } else {
                        System.out.println("\nClasa '" + targetClass.getName() + "' este non-publica si va fi ignorata pentru testare directa.");
                    }
                    processedClasses.add(className); // marcheaza clasa ca procesata

                } catch (ClassNotFoundException e) {
                    System.err.println("Eroare: Clasa '" + className + "' nu a fost gasita de classloader-ul personalizat. " + e.getMessage());
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

    // navigheaza recursiv prin foldere ptr a gasi fisiere .class
    private static void findClassFilesRecursively(File directory, String packageName, List<String> classNames) {
        if (!directory.isDirectory()) {
            return;
        }
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                String newPackageName = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                findClassFilesRecursively(file, newPackageName, classNames);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName.isEmpty() ?
                        file.getName().replace(".class", "") :
                        packageName + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

    // extrage numele claselor dintr-un fisier JAR
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

    // ruleaza testele dintr-o clasa data
    private static void runTestsInClass(Class<?> targetClass) {
        System.out.println("\n  Executare metode @Test in clasa " + targetClass.getName() + ":");
        Object instance = null;

        // non-static -> incarcam doar o data instanta clasei
        // constructor fara argumente (cautam) -> ca sa invocam metodele non-statice
        if (!Modifier.isAbstract(targetClass.getModifiers()) && !targetClass.isInterface()) {
            try {
                Constructor<?> constructor = targetClass.getDeclaredConstructor();
                constructor.setAccessible(true); // daca e privat, tot permitem
                instance = constructor.newInstance();
                System.out.println("Instanta clasei creata cu succes pentru testele non-statice.");
            } catch (NoSuchMethodException e) {
                System.out.println("Clasa '" + targetClass.getName() + "' nu are un constructor fara argumente. Metodele non-statice @Test NU vor fi executate.");
            } catch (Exception e) {
                System.err.println("Nu s-a putut crea instanta pentru clasa " + targetClass.getName() + ": " + e.getMessage());
            }
        }

        for (Method method : targetClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                totalTestMethodsFound++;
                System.out.println("    Tentativa de executare: " + method.getName() + "()");
                totalTestsExecuted++;

                // daca e privata, tot o facem accesibbila
                method.setAccessible(true);

                try {
                    if (Modifier.isStatic(method.getModifiers())) { //verificam daca e static
                        Object[] mockArgs = generateMockArguments(method.getParameterTypes());
                        method.invoke(null, mockArgs); // static -> instanta e null
                        totalTestsPassed++;
                    } else {
                        // non-statice -> avem nevoie de o instanta
                        if (instance != null) {
                            Object[] mockArgs = generateMockArguments(method.getParameterTypes());
                            method.invoke(instance, mockArgs); // apelam instanta
                            totalTestsPassed++;
                        } else {
                            System.out.println("      IGNORAT: Metoda '" + method.getName() + "' este non-statica, dar nu s-a putut crea o instanta a clasei.");
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
                    System.err.println("      FAILED: Testul '" + method.getName() + "' a esuat cu o eroare neasteptata: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    // generam valori mock pt argumentele metodelor
    private static Object[] generateMockArguments(Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i]; //ia timpul fiecarui parametru (gen, int, String)
            if (type == int.class || type == Integer.class) {
                args[i] = 42; // Valoare mock pentru int
            } else if (type == String.class) {
                args[i] = "mock_string_" + (i + 1); // Valoare mock pentru String
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
                System.out.println("Tip de argument '" + type.getName() + "' nu este suportat pentru generare mock. Se foloseste null.");
            }
        }
        return args;
    }
}