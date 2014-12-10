package com.github.knives.groovy.transform

/**
 * @CompileStatic is to improve performance
 * 
 * https://sites.google.com/a/athaydes.com/renato-athaydes/posts/experimentingwithgroovycompilestatic
 */

import org.junit.Test
import groovy.transform.CompileStatic

class BackToJava {
	
	@CompileStatic
	private double distance(double v1, double v2) {
		return v2 - v1
	}
	
	void testStaticDistance() {
		println distance(2.0, 3.0)
	}
}

