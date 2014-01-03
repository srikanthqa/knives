package com.github.knives.bean.jvm;

public class JvmAnnotation {
	final private String name;
	
	private JvmAnnotation(String name) {
		this.name = name;
	}
	
	public static JvmAnnotationBuilder create() {
		return new JvmAnnotationBuilder();
	}
	
	public static class JvmAnnotationBuilder {
		private JvmAnnotationBuilder() { }
	}
}
