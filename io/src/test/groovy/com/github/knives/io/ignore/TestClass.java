package com.github.knives.io.ignore;

import java.util.List;

import com.github.knives.io.ignore.TestAnnotation.TestAnnotationConstructor;
import com.github.knives.io.ignore.TestAnnotation.TestAnnotationField;
import com.github.knives.io.ignore.TestAnnotation.TestAnnotationMethod;
import com.github.knives.io.ignore.TestAnnotation.TestAnnotationParameter;
import com.github.knives.io.ignore.TestAnnotation.TestAnnotationType;

@TestAnnotationType
final public class TestClass {

	public static String staticPublicField;
	final static public String staticFinalPublicField = null;
	
	@TestAnnotationField
	private String memberAnnotatedField;
	
	private String memberFieldString;
	private int memberFieldInteger;
	private Integer memberFieldIntegerType;
	private boolean memberFieldBoolean;
	private Boolean memberFieldBooleanType;
	
	private volatile boolean memberVolatile;
	
	private transient boolean memberTransient;
	
	@TestAnnotationConstructor
	public TestClass() {}
	
	@TestAnnotationMethod
	public void testMethod() {}
	
	static public void testStaticMethod() {}
	
	public void testAnnotationParameter(@TestAnnotationParameter Object parameter) {}
	
	public void testVararg(String ... args) {}
	
	public synchronized void testSynchronizedMethod() {}
	
	public static <T extends Comparable <? super T > > void sort(List<T> list) { 
	}
	
	public class InnerClass { }
	
	public static class StaticInnerClass { }
	
}
