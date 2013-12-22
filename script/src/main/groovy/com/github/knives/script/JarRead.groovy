package com.github.knives.script
/**
 * http://cs.dvc.edu/HowTo_ReadJars.html
 */

import java.util.jar.*

if (args.length != 2) {
	System.out.println("Please provide a JAR filename and file to read");
	System.exit(-1);
}

JarFile jarFile = new JarFile(args[0]);
JarEntry entry = jarFile.getJarEntry(args[1]);
InputStream input = jarFile.getInputStream(entry);
process(input);

def process(InputStream input) throws IOException {
	InputStreamReader isr = new InputStreamReader(input);
	BufferedReader reader = new BufferedReader(isr);
	String line;
	while ((line = reader.readLine()) != null)
		System.out.println(line);
	reader.close();
}
