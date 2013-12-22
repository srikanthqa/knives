package com.github.knives.script
/**
 * Taken example to write your custom class loader
 * 
 * http://kalanir.blogspot.com/2010/01/how-to-write-custom-class-loader-to.html
 */

import java.util.jar.JarEntry
import java.util.jar.JarFile

public class JarClassLoader extends ClassLoader {
	final private String jarFile;
	private Hashtable classes = new Hashtable<String, Class<?>>(); //used to cache already defined classes

	public JarClassLoader(String jarFile) {
		super(JarClassLoader.class.getClassLoader()); //calls the parent class loader's constructor
		this.jarFile = jarFile;
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return findClass(className);
	}

	public Class<?> findClass(final String className) {
		Class<?> result = classes.get(className);

		if (result != null) {
			return result;
		}

		try {
			return findSystemClass(className);
		} catch (Exception ignore) { } 

		try {
			final JarFile jar = new JarFile(jarFile)
			final JarEntry entry = jar.getJarEntry(className.replace('.', '/') + ".class")
			final InputStream is = jar.getInputStream(entry)

			final byte[] classByte = is.getBytes();
			result = defineClass(className, classByte, 0, classByte.length, null)
			classes.put(className, result);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		final JarClassLoader classLoader = new JarClassLoader(args[0])
		println classLoader.findClass(args[1])
	}
}
