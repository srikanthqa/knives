package com.github.knives.groovy.transform

import org.junit.Test

class ThereCanOnlyBeOneNeo {
	@Singleton 
	static class Neo {
		final def name = "Neo" 
	}
	
	@Test
	void testSingleton() {
		assert Neo.getInstance() instanceof Neo
		assert Neo.instance instanceof Neo
		
		try {
			new Neo()
		} catch (ignore) { assert true }
	}
}