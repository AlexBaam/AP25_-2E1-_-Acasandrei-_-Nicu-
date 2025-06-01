package org.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.Enumeration;

public class CustomClassLoader extends URLClassLoader {

    // folosim ClassLoader de sistem ca parinte
    public CustomClassLoader(URL[] urls) {
        super(urls, ClassLoader.getSystemClassLoader());
    }

    // incarca o clasa dintr-un array de bytes (continutul unui fisier .class)
    public Class<?> defineClassFromBytes(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }

    // incarca o clasa dintr-un fisier .class
    public Class<?> findClassInFile(File file) throws ClassNotFoundException {
        try {
            // construim numele complet
            // ex: folder/folder2/nume_clasa.class -> folder.folder2.nume_clasa.class
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
