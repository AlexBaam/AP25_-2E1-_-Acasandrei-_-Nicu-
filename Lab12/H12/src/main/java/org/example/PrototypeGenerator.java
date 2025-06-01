package org.example;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PrototypeGenerator {

    public static String generatePrototype(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        // extrage si converteste modificatori (public, static, final etc.), tip ( gen: class, interface, enum, annotation), nume
        int modifiers = clazz.getModifiers();
        sb.append(Modifier.toString(modifiers)).append(" ");

        if (clazz.isAnnotation()) {
            sb.append("@interface ");
        } else if (clazz.isEnum()) {
            sb.append("enum ");
        } else if (clazz.isInterface()) {
            sb.append("interface ");
        } else {
            sb.append("class ");
        }

        sb.append(clazz.getSimpleName());

        // tipurile generice (gen: T, E etc.)
        // gen, chestii de genul asta: <T extends Comparable>
        TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
        if (typeParameters.length > 0) {
            sb.append("<").append(Arrays.stream(typeParameters)//le face string
                            .map(TypeVariable::getName)
                            .collect(Collectors.joining(", ")))
                    .append(">");
        }
        sb.append(" ");

        //  Superclasa -> doar pt clase non-interfete/enum/adnotari
        //  gen, chestii de genul: extends NumeSuperClasa
        if (!clazz.isInterface() && !clazz.isAnnotation() && !clazz.isEnum()) {
            Class<?> superClass = clazz.getSuperclass(); //luam clasa parinte
            if (superClass != null && !superClass.equals(Object.class)) { //verificam sa nu fie dansa
                sb.append("extends ").append(superClass.getName()).append(" ");
            }
        }

        // interfate
        // ex: implements Interfata1, Interfata2
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            sb.append(clazz.isInterface() ? "extends " : "implements ")
                    .append(Arrays.stream(interfaces)
                            .map(Class::getName)
                            .collect(Collectors.joining(", ")))
                    .append(" ");
        }

        sb.append("{\n");

        // campuri (variabile)
        for (Field field : clazz.getDeclaredFields()) {
            sb.append("  ").append(Modifier.toString(field.getModifiers())).append(" ");
            sb.append(field.getType().getName()).append(" ");
            sb.append(field.getName()).append(";\n");
        }
        sb.append("\n");

        // constructori
        //  public MyClass(java.lang.String arg0);
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            sb.append("  ").append(Modifier.toString(constructor.getModifiers())).append(" ");
            sb.append(clazz.getSimpleName());
            sb.append("(");
            sb.append(Arrays.stream(constructor.getParameterTypes())
                    .map(Class::getName)
                    .collect(Collectors.joining(", ")));
            sb.append(")");
            // exceptii declarate
            // ex : throws IOException
            Class<?>[] exceptionTypes = constructor.getExceptionTypes();
            if (exceptionTypes.length > 0) {
                sb.append(" throws ").append(Arrays.stream(exceptionTypes)
                        .map(Class::getName)
                        .collect(Collectors.joining(", ")));
            }
            sb.append(";\n");
        }
        sb.append("\n");

        // 7. Metode
        for (Method method : clazz.getDeclaredMethods()) {
            sb.append("  ").append(Modifier.toString(method.getModifiers())).append(" ");
            sb.append(method.getReturnType().getName()).append(" "); //(void, int, String)
            sb.append(method.getName());
            sb.append("(");
            sb.append(Arrays.stream(method.getParameterTypes())
                    .map(Class::getName)
                    .collect(Collectors.joining(", ")));
            sb.append(")");
            // adauga exceptii declarate
            Class<?>[] exceptionTypes = method.getExceptionTypes();
            if (exceptionTypes.length > 0) {
                sb.append(" throws ").append(Arrays.stream(exceptionTypes)
                        .map(Class::getName)
                        .collect(Collectors.joining(", ")));
            }
            sb.append(";\n");
        }

        sb.append("}\n");
        return sb.toString();
    }
}
