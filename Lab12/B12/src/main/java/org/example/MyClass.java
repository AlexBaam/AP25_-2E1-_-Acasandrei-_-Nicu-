package org.example;

public class MyClass {

    public String publicField = "Aici avem un camp public";
    private int privateField = 100;

    public MyClass() {
        System.out.println("Am apelat constructorul lui MyClass");
    }

    public void publicMethod() {
        System.out.println("Metoda publica 'publicMethod' a fost apelata.");
    }

    private void privateMethod() {
        System.out.println("Metoda privata 'privateMethod' a fost apelata.");
    }

    public static void anotherStaticMethod() {
        System.out.println("Metoda statica 'anotherStaticMethod' a fost apelata.");
    }

    @Test
    public static void testMethod1() {
        System.out.println("--- TestMethod1: Metoda de test statica, fara argumente. ---");
    }

    @Test
    public static void testMethod2() {
        System.out.println("--- TestMethod2: O alta metoda de test statica. ---");
    }

    public void ordinaryMethod() {
        System.out.println("O metoda obisnuita, nu este test.");
    }
}
