package org.example;


@Test
public class MyClass2 {

    public String name;
    private int id;

    public  MyClass2() {
        this("Default Name", 0);
    }

    public  MyClass2(String name, int id) {
        this.name = name;
        this.id = id;
        System.out.println(" MyClass2 constructor: " + name + ", " + id);
    }

    @Test
    public static void staticTestNoArgs() {
        System.out.println("static - testMethod: staticTestNoArgs() executat.");
    }

    @Test
    public void nonStaticTestNoArgs() {
        System.out.println("non-static - testMethod: nonStaticTestNoArgs() executat.");
        System.out.println("Valoare 'name' in instanta: " + this.name);
    }

    @Test
    public static void staticTestWithInt(int value) {
        System.out.println("static - testMethod: staticTestWithInt(" + value + ") executat.");
    }

    @Test
    public void nonStaticTestWithIntAndString(int num, String text) {
        System.out.println("non-static - testMethod: nonStaticTestWithIntAndString(" + num + ", \"" + text + "\") executat.");
    }

    public void regularMethod() {
        System.out.println("metoda obisnuita");
    }

    // arunca exceptii pt -> testam tratarea erorilor
    @Test
    public void testMethodThrowsException() {
        System.out.println("non-static - testMethod: testMethodThrowsException() - va arunca o exceptie.");
        throw new RuntimeException("Eroare simulata in test!");
    }
}