package org.example;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/*
cd src/main/java
javac org/example/*.java
java -cp . org.example.TestRunner org.example.MyClass

 */

public class TestRunner {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Hopa, nu e bine");
            return;
        }

        String className = args[0];

        /*
        Class.forName(className) - metoda cheie pt a incarca o clasa in memorie dinamic
                                 - ii dai numele complet al clasei (cu tot cu pachet -> com.example.MyClass)
                                 - returneaza un obiect de tip Class<?>
                      *obiect Class - poarta de acces catre toate informatiile despre clasa respectiva
        targetClass.getDeclaredConstructors()- returneaza un array cu toti constructorii declarati in clasa
        targetClass.getDeclaredFields() - returneaza un array cu toate campurile (variabilele) declarate in clasa
        targetClass.getDeclaredMethods() - returneaza un array cu toate metodele declarate in clasa
        method.isAnnotationPresent(Test.class) - aceasta verifica daca o metoda are adnotarea @Test
                                               - daca da, trece mai departe
        Modifier.isStatic(method.getModifiers()) - fiecare metoda are un set de modificatori (gen, public, private, static, final etc.)
                      * getModifiers() - returneaza un int care contine acesti modificatori
                      * Modifier.isStatic() - o metoda ajutatoare care verifica daca bitul corespunzator pt static e setat
         method.invoke(null) - apeleaza metode
                             - primul argument este instanta obiectului pe care vrem sa apelam metoda
                             - daca metoda e static -> nu avem nevoie de o instanta -> punem null
                             - al doilea argument (optional) -> un array de obiecte
                             - reprezinta argumentele metodei
                             - daca metoda nu are argumente -> nu punem nimic SAU punem null.
         */

        try {

            //incarcam clasa in memorie  dinamic -> cu tot cu pachet
            // -> returneaza un obiect de tip Class<?> (calea ca sa aflam toate informatiile)

            Class<?> targetClass = Class.forName(className);
            System.out.println("Clasa incarcata: " + targetClass.getName());
            System.out.println("----------------------------------------");


            // extragem informatiile despre clasa colectata:
            System.out.println("Informatii despre clasa " + targetClass.getName() + ":");


            System.out.println("Nume simplu: " + targetClass.getSimpleName());

            // (cu pachet)
            System.out.println("Nume complet: " + targetClass.getName());

            System.out.println("Pachet: " + targetClass.getPackage().getName());

            System.out.println("Superclasa: " + targetClass.getSuperclass().getName());

            System.out.println("Interfete: " + Arrays.toString(targetClass.getInterfaces()));


            // Constructori
            System.out.println("\nConstructori:");
            for (java.lang.reflect.Constructor<?> constructor : targetClass.getDeclaredConstructors()) {
                System.out.println("  " + constructor.toGenericString());
            }

            // (variabile) -> indiferent de modificator (public, private)
            System.out.println("\nCampuri:");
            for (java.lang.reflect.Field field : targetClass.getDeclaredFields()) {
                System.out.println("  " + field.toGenericString());
            }


            // getDeclaredMethods() - returneaza toate metodele declarate in clasa (publc, private)
            // getMethods() - returneaza doar metodele publice (incluzand cele mostenite !!!! ATENTIE).
            System.out.println("\nMetode:");
            for (Method method : targetClass.getDeclaredMethods()) {
                System.out.println("  " + method.toGenericString());
            }
            System.out.println("----------------------------------------");


            // invocam metodele statice fara argumente (adnotate cu @Test)
            System.out.println("\nExecutare teste (@Test metode statice, fara argumente):");
            int testsFound = 0;
            int testsPassed = 0;

            for (Method method : targetClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) { //verificam daca metoda are @Test
                    if (Modifier.isStatic(method.getModifiers())) { // verificam daca e statica
                        if (method.getParameterCount() == 0) { //verificam daca nu are argument
                            testsFound++;
                            System.out.println("  Executare test: " + method.getName() + "()");
                            try {
                                method.invoke(null); //invocam metodele
                                testsPassed++;
                            } catch (Exception e) {
                                System.err.println("    Eroare la executarea " + method.getName() + ": " + e.getCause());
                            }
                        } else {
                            System.out.println("  Metoda @Test '" + method.getName() + "' ignorata: are argumente.");
                        }
                    } else {
                        System.out.println("  Metoda @Test '" + method.getName() + "' ignorata: nu este statica.");
                    }
                }
            }
            System.out.println("\nStatistici teste (Compulsory):");
            System.out.println("  Teste statice (@Test) gasite si executate: " + testsFound);
            System.out.println("  Teste statice (@Test) executate cu succes: " + testsPassed);

        } catch (ClassNotFoundException e) {
            System.err.println("Eroare: Clasa '" + className + "' nu a fost gasita");
        } catch (Exception e) {
            System.err.println("A aparut o eroare neasteptata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}