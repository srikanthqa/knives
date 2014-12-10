package com.github.knives.groovy.transform

import org.junit.Test
import groovy.transform.Immutable


/**
 * No mutators (methods that modify internal state)
 * Class must be final
 * Fields must be private and final
 * Defensive copying of mutable components equals, hashCode and toString 
 * must be implemented in terms of the fields if you want to compare your objects or 
 * use them as keys in e.g. maps
 * 
 * http://groovy.codehaus.org/Immutable+AST+Macro
 */

class ImmutableMagicBean {
	@Immutable 
	class Punter {
		String first, last
	}

	@Test
	void testImmutable() {
		println new Punter(first: 'hello', last: 'world')
	}
}