package com.github.knives.java.classloader;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;

import com.google.common.io.ByteStreams;

/**
 * Taken example to write your custom class loader
 * 
 * http://kalanir.blogspot.com/2010/01/how-to-write-custom-class-loader-to.html
 */
public class JarClassLoaderTest {

	public static class JarClassLoader extends ClassLoader {
		final private String jarFile;
		
		//used to cache already defined classes
		
		final private Hashtable<String, Class<?>> classes = new Hashtable<String, Class<?>>(); 

		public JarClassLoader(String jarFile) {
			super(JarClassLoader.class.getClassLoader()); //calls the parent class loader's constructor
			this.jarFile = jarFile;
		}

		@Override
		public Class<?> loadClass(String className) throws ClassNotFoundException {
			return findClass(className);
		}

		@Override
		public Class<?> findClass(final String className) throws ClassNotFoundException {
			Class<?> result = classes.get(className);

			if (result != null) {
				return result;
			}

			try {
				return findSystemClass(className);
			} catch (Exception ignore) { } 

			try {
				final JarFile jar = new JarFile(jarFile);
				final JarEntry entry = jar.getJarEntry(className.replace('.', '/') + ".class");
				final InputStream is = jar.getInputStream(entry);

				final byte[] classByte = ByteStreams.toByteArray(is);
				result = defineClass(className, classByte, 0, classByte.length, null);
				classes.put(className, result);
				return result;
			} catch (Exception e) {
				throw new ClassNotFoundException(className + " is not found", e);
			}
		}
	}
	
	@Test
	public void test() {
	}

}
