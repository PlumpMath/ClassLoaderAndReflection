package ru.svetozarov.reflection.CustomLoader;

import java.io.*;
import java.net.URL;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by E. Svetozarov on 15.02.2017.
 */
public class JarClassLoared extends  ClassLoader {
    private String jarFile = "https://github.com/evgenij120693/lessionCarShopLogger/raw/master/Auto.jar"; //Path to the jar file
    private Hashtable classes = new Hashtable(); //used to cache already defined classes

    public JarClassLoared() {
        super(JarClassLoared.class.getClassLoader()); //calls the parent class loader's constructor
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }

    public Class findClass(String className) {
        byte classByte[];
        Class result = null;

        result = (Class) classes.get(className); //checks in cached classes
        if (result != null) {
            System.out.println("Find!");
            return result;
        }
        try {
            return findSystemClass(className);
        } catch (Exception e) {

        }

        try {
            downloadUsingStream(jarFile, "src\\" + className+".jar");
            JarFile jar = new JarFile("src\\" + className+".jar");
            JarEntry entry = jar.getJarEntry(className + ".class");

            InputStream is = jar.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = is.read();
            while (-1 != nextValue) {
                byteStream.write(nextValue);
                nextValue = is.read();
            }

            classByte = byteStream.toByteArray();

            result = defineClass("ru.svetozarov.reflection.objects." + className, classByte, 0, classByte.length, null);
            classes.put(className, result);

            return result;
        } catch (Exception e) {
            return null;
        }
    }


    private static void downloadUsingStream(String urlStr, String file) throws IOException {
        System.out.println("Download start...");
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();

    }
}
