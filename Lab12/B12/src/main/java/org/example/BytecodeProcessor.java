package org.example;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class BytecodeProcessor {

    public static byte[] getClassBytes(ClassLoader classLoader, String className) throws IOException {
        String resourceName = className.replace('.', '/') + ".class";
        try (InputStream is = classLoader.getResourceAsStream(resourceName)) {
            if (is == null) {
                return null;
            }
            return is.readAllBytes();
        }
    }

    // adauga System.out.println la onceput pt ca sa afisam
    public static byte[] instrumentClass(byte[] originalBytes, String methodName) {
        ClassReader cr = new ClassReader(originalBytes);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES); //constr unul nou

        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals(methodName)) {
                    System.out.println("  Instrumenting method: " + name + descriptor);
                    return new AdviceAdapter(Opcodes.ASM9, mv, access, name, descriptor) {
                        @Override
                        protected void onMethodEnter() {
                            // Injectam System.out.println("Metoda '" + methodName + "' a fost apelata!");
                            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            mv.visitLdcInsn("ASM: Metoda '" + methodName + "' a fost apelata!");
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                        }
                    };
                }
                return mv;
            }
        };

        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    // ex -> generare dinamica clasa
    public static Class<?> generateDynamicClass(String className) {
        ClassWriter cw = new ClassWriter(0);

        // definim clasa de mai sus
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
                className.replace('.', '/'), null, "java/lang/Object", null);

        // constructorul implicit
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        //apelam si superclasa
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // metoda statica -> e cea cu salutul
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "sayHello", "()V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(2, 0);
        mv.visitEnd();

        cw.visitEnd();

        byte[] classBytes = cw.toByteArray();

        // incarcam clasa
        try {
            return new CustomClassLoader(new URL[]{}).defineClassFromBytes(className, classBytes);
        } catch (Exception e) {
            System.err.println("Eroare la incarcarea clasei generate dinamic: " + e.getMessage());
            return null;
        }
    }
}

