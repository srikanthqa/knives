package com.github.knives.groovy.transform

import org.junit.Test
import groovy.util.ConfigSlurper


class ArrayConfigurationExample {
	@Test
	void testConfigSlurper() {
		def exampleDsl = """
tests {
	testX {
		parameter1 = 1
	}

	testY {
		parameter1 = 3
	}

	testZ {
		parameter1 = 2
	}
}	
"""
		
		// reading the array
		def config = new ConfigSlurper().parse(exampleDsl)
		config.tests.each { key, value ->
			println "Test: ${key}"
			println "${value}"
		}
	}	
}
