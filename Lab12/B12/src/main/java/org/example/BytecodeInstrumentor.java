package org.example;

import javassist.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BytecodeInstrumentor {

    public static void instrumentClass(String className, String outputDir) throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();

        System.out.println("\n--- Instrumentare Bytecode pentru " + className + " ---");
        System.out.println("  Injectat cod in 'nonStaticTestNoArgs'.");
        System.out.println("  Metoda 'injectedMethod' adaugata dinamic.");
        ///System.out.println("  Clasa modificata salvata in: " + outputPath);

        // Adaugam directorul curent la ClassPool ca sa poata gasi clasele compilate
        pool.appendClassPath(outputDir);

        CtClass ctClass = pool.get(className); // Obtinem reprezentarea Javassist a clasei

        System.out.println("\n--- Instrumentare Bytecode pentru " + className + " ---");

        // Exemplu 1: Injectarea de cod intr-o metoda existenta
        try {
            CtMethod method = ctClass.getDeclaredMethod("nonStaticTestNoArgs");
            // Adaugam cod la inceputul metodei
            method.insertBefore("System.out.println(\"    [Javassist] Cod injectat inainte de nonStaticTestNoArgs!\");");
            // Adaugam cod la sfarsitul metodei
            method.insertAfter("System.out.println(\"    [Javassist] Cod injectat dupa nonStaticTestNoArgs!\");");
            System.out.println("  Injectat cod in 'nonStaticTestNoArgs'.");
        } catch (NotFoundException e) {
            System.out.println("  Metoda 'nonStaticTestNoArgs' nu a fost gasita pentru injectie. Ignorat.");
        }

        // Exemplu 2: Adaugarea unei noi metode dinamice
        try {
            CtMethod newMethod = CtMethod.make(
                    "public void injectedMethod() { System.out.println(\"    [Javassist] Aceasta este o metoda injectata dinamic!\"); }",
                    ctClass);
            ctClass.addMethod(newMethod);
            System.out.println("  Metoda 'injectedMethod' adaugata dinamic.");
        } catch (CannotCompileException e) {
            System.err.println("  Eroare la adaugarea metodei injectate: " + e.getMessage());
        }

        // Exemplu 3: Generarea dinamica a unei clase noi (fara a o adauga la cea existenta)
        // CtClass newDynamicClass = pool.makeClass("com.example.DynamicGeneratedClass");
        // CtMethod dynamicMethod = CtMethod.make("public static void performDynamicAction() { System.out.println(\"Hello from dynamic class!\"); }", newDynamicClass);
        // newDynamicClass.addMethod(dynamicMethod);
        // newDynamicClass.writeFile(outputDir); // Salveaza clasa noua

        // Salvam clasa modificata pe disc
        Path outputPath = Paths.get(outputDir, className.replace('.', File.separatorChar) + ".class");
        Files.createDirectories(outputPath.getParent()); // Asigura-te ca directorul exista
        ctClass.writeFile(outputDir);
        System.out.println("  Clasa modificata salvata in: " + outputPath);
    }
}
