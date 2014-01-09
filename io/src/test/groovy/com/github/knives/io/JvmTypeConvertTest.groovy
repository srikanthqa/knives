package com.github.knives.io;

import static org.junit.Assert.*

import org.junit.Test

import com.github.knives.io.ignore.TestAbstractClass;
import com.github.knives.io.ignore.TestAnnotation;
import com.github.knives.io.ignore.TestClass
import com.github.knives.io.ignore.TestEnum
import com.github.knives.io.ignore.TestInterface

public class JvmTypeConvertTest {

	@Test
	void testType() {
		def jvmType = new JvmTypeConverter().convert(getClass(TestClass.class))
		assertEquals(TestClass.class.getCanonicalName(), jvmType.getName())
		
	}
	
	@Test
	void testInterface() {
		def jvmType = new JvmTypeConverter().convert(getClass(TestInterface.class))
		assertEquals(TestInterface.class.getCanonicalName(), jvmType.getName())
	}
	
	@Test 
	void testAnnotation() {
		def jvmType = new JvmTypeConverter().convert(getClass(TestAnnotation.class))
		assertEquals(TestAnnotation.class.getCanonicalName(), jvmType.getName())
	}
	
	@Test
	void testEnum() {
		def jvmType = new JvmTypeConverter().convert(getClass(TestEnum.class))
		assertEquals(TestEnum.class.getCanonicalName(), jvmType.getName())
		
	}
	
	@Test
	public testAbstractClass() {
		def jvmType = new JvmTypeConverter().convert(getClass(TestAbstractClass.class))
		assertEquals(TestAbstractClass.class.getCanonicalName(), jvmType.getName())
	}
	
	@Test
	public testExtendedClass() {
		
	}
	
	private InputStream getClass(Class cls) {
		String name = cls.getCanonicalName()
		String resName = '/' + name.replaceAll('\\.', '/') + '.class';
		return cls.getResourceAsStream(resName);
	}

}
