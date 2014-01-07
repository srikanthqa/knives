package com.github.knives.io;

import static org.junit.Assert.*

import org.junit.Test

import com.github.knives.io.ignore.TestClass

public class JvmTypeConvertTest {

	@Test
	void testStaticField() {
		def jvmType = new JvmTypeConverter().convert(getClass(TestClass.class))
		assertEquals(TestClass.class.getCanonicalName(), jvmType.getName())
	}
	
	@Test
	void testField() {
		
	}
	
	@Test
	void testVoidMethod() {
		
	}
	
	
	@Test
	void testSingleArgMethod() {
		
	}
	
	@Test
	void testVaragsMethod() {
		
	}
	
	@Test
	void testMultipleArgsMethod() {
		
	}
	
	@Test
	void testStaticMethod() {
		
	}
	
	@Test 
	void testAnnotation() {
		
	}
	
	@Test
	void testTypeAnnotation() {
		
	}
	
	@Test
	void testMethodAnnotation() {
		
	}
	
	@Test
	void testFieldAnnotation() {
		
	}
	
	@Test
	void testArgAnnotation() {
		
	}
	
	@Test
	void testEnum() {
		
	}
	
	private InputStream getClass(Class cls) {
		String name = cls.getCanonicalName()
		String resName = '/' + name.replaceAll('\\.', '/') + '.class';
		return cls.getResourceAsStream(resName);
	}

}
