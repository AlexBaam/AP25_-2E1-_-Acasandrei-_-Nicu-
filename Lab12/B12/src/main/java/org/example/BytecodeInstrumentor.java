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

        pool.appendClassPath(outputDir);

        CtClass ctClass = pool.get(className);

        System.out.println("\n--- Instrumentare Bytecode pentru " + className + " ---");

        try {
            CtMethod method = ctClass.getDeclaredMethod("nonStaticTestNoArgs");

            method.insertBefore("System.out.println(\"    [Javassist] Cod injectat inainte de nonStaticTestNoArgs!\");");
            method.insertAfter("System.out.println(\"    [Javassist] Cod injectat dupa nonStaticTestNoArgs!\");");
            System.out.println("  Injectat cod in 'nonStaticTestNoArgs'.");
        } catch (NotFoundException e) {
            System.out.println("  Metoda 'nonStaticTestNoArgs' nu a fost gasita pentru injectie. Ignorat.");
        }

        try {
            CtMethod newMethod = CtMethod.make(
                    "public void injectedMethod() { System.out.println(\"    [Javassist] Aceasta este o metoda injectata dinamic!\"); }",
                    ctClass);
            ctClass.addMethod(newMethod);
            System.out.println("  Metoda 'injectedMethod' adaugata dinamic.");
        } catch (CannotCompileException e) {
            System.err.println("  Eroare la adaugarea metodei injectate: " + e.getMessage());
        }

        Path outputPath = Paths.get(outputDir, className.replace('.', File.separatorChar) + ".class");
        Files.createDirectories(outputPath.getParent());
        ctClass.writeFile(outputDir);
        System.out.println("  Clasa modificata salvata in: " + outputPath);
    }
}
