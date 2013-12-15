/**
 * Taken example to write your custom class loader
 * 
 * http://kalanir.blogspot.com/2010/01/how-to-write-custom-class-loader-to.html
 */

import java.util.jar.JarEntry
import java.util.jar.JarFile

public class JarClassLoader extends ClassLoader {
	private String jarFile = "jar/test.jar"; //Path to the jar file
	private Hashtable classes = new Hashtable<String, Class<?>>(); //used to cache already defined classes

	public JarClassLoader() {
		super(JarClassLoader.class.getClassLoader()); //calls the parent class loader's constructor
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return findClass(className);
	}

	public Class<?> findClass(String className) {
		byte[] classByte;
		Class<?> result = classes.get(className);

		if (result != null) {
			return result;
		}

		try {
			return findSystemClass(className);
		} catch (Exception ignore) { } 

		try {
			JarFile jar = new JarFile(jarFile);
			JarEntry entry = jar.getJarEntry(className + ".class");
			InputStream is = jar.getInputStream(entry);
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			int nextValue = is.read();
			while (-1 != nextValue) {
				byteStream.write(nextValue);
				nextValue = is.read();
			}

			classByte = byteStream.toByteArray();
			result = defineClass(className, classByte, 0, classByte.length, null);
			classes.put(className, result);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

}
