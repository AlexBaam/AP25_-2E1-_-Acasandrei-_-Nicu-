package org.example;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class JavaSourceCompiler {

    public static boolean compile(List<File> javaFiles, File outputDirectory) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("Eroare: Java Development Kit (JDK) nu este instalat sau nu este in PATH. Pentru compilare, este necesar un JDK, nu doar JRE.");
            return false;
        }

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.getDefault(), null);

        // Optiuni de compilare: -d <outputDirectory> (unde sa puna fisierele .class)
        List<String> options = new ArrayList<>();
        options.add("-d");
        options.add(outputDirectory.getAbsolutePath());
        options.add("-source"); // Specificam versiunea sursei
        options.add("11"); // Ex: Java 11
        options.add("-target"); // Specificam versiunea target
        options.add("11"); // Ex: Java 11

        // FOARTE IMPORTANT: Spunem compilatorului unde să caute fișierele sursă
        options.add("-sourcepath");
        // Presupunem că rădăcina surselor este "src/main/java" relativ la directorul de lucru
        options.add(new File("src/main/java").getAbsolutePath());

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);

        boolean success = task.call();

        // Afisarea diagnosticelor (erorilor/avertismentelor) de compilare
        diagnostics.getDiagnostics().forEach(diagnostic -> {
            // Adaugam verificarea pentru null la diagnostic.getSource()
            String sourceName = (diagnostic.getSource() != null) ? diagnostic.getSource().getName() : "necunoscut";
            System.err.println("Compilare: " + diagnostic.getKind() + ": " + diagnostic.getMessage(Locale.getDefault()) +
                    " (Linia: " + diagnostic.getLineNumber() + ", Coloana: " + diagnostic.getColumnNumber() +
                    " in " + sourceName + ")");
        });

        try {
            fileManager.close();
        } catch (IOException e) {
            System.err.println("Eroare la inchiderea file manager-ului: " + e.getMessage());
        }

        return success;
    }
}