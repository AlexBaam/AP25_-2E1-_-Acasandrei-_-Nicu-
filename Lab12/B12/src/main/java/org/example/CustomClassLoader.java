package org.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader extends URLClassLoader {

    public CustomClassLoader(URL[] urls) {
        super(urls, ClassLoader.getSystemClassLoader());
    }

    // Constructor adăugat pentru a permite crearea unui ClassLoader fără URL-uri inițiale
    public CustomClassLoader() {
        super(new URL[]{}, ClassLoader.getSystemClassLoader());
    }

    public Class<?> defineClassFromBytes(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }

    // Această metodă nu va mai fi folosită direct în `main` deoarece compilarea se face separat
    // Dar o păstrăm pentru coerență
    public Class<?> findClassInFile(File file) throws ClassNotFoundException {
        try {
            String className = file.getPath()
                    .replace(File.separatorChar, '.')
                    .replace(".class", "");

            byte[] classData = loadClassData(file);
            return defineClassFromBytes(className, classData);

        } catch (IOException e) {
            throw new ClassNotFoundException("Could not load class from file: " + file.getAbsolutePath(), e);
        }
    }

    private byte[] loadClassData(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bytesRead;
        byte[] data = new byte[4096];
        while ((bytesRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        is.close();
        return buffer.toByteArray();
    }
}