package com.github.knives.groovy.transform

import static org.junit.Assert.*
import org.junit.Test


class BeautifulSwitch {
	def doSwitch(val) {
		def result
		switch (val) {
			case ~/^Switch.*Groovy$/:
				result = 'Pattern match'
				break
			case BigInteger:
				result = 'Class isInstance'
				break
			case 60..90:
				result = 'Range contains'
				break
			case [21, 'test', 9.12]:
				result = 'List contains'
				break
			case 42.056:
				result = 'Object equals'
				break
			case { it instanceof Integer && it < 50 }:
				result = 'Closure boolean'
				break
			default:
				result = 'Default'
				break
		}
		result
	}

	@Test
	void testSwitch() {
		assertEquals('Pattern match', doSwitch("Switch to Groovy"))
		assertEquals('Class isInstance', doSwitch(42G))
		assertEquals('Range contains', doSwitch(70))
		assertEquals('List contains', doSwitch('test'))
		assertEquals('Object equals', doSwitch(42.056))
		assertEquals('Closure boolean', doSwitch(20))
		assertEquals('Default', doSwitch('default'))
	}
}