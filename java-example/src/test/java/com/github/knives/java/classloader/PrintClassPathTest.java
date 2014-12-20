package com.github.knives.java.classloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrintClassPathTest {

	@Test
	public void test() {
		printClassPath(this.getClass().getClassLoader());
	}

	public void printClassPath(ClassLoader classLoader) {
		System.out.println(classLoader.toString());
		if (classLoader.getParent() != null) {
			printClassPath(classLoader.getParent());
		}
	}
}
