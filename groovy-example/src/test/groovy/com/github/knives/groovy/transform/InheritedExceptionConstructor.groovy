package com.github.knives.groovy.transform

import groovy.transform.InheritConstructors

import org.junit.Test

class InheritedExceptionConstructor {

	@InheritConstructors
	static class SomeNewException extends Exception { }

	//@Test
	void testInheritedConstructors() {	
		println (new SomeNewException('some message'))
		println new SomeNewException('some message', new Exception())
		println new SomeNewException(new Exception())
		println new SomeNewException()
	}
}