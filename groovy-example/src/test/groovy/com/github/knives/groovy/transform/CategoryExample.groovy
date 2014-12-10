package com.github.knives.groovy.transform

import org.junit.Test

/**
 * Use unobtrusive category in groovy
 */

class CategoryExample {
	static class LoopCategory {
		// retry until max or some condition return true
		static void until(Range range, Closure closure) {
			range.any { closure.call(it) }
		}
		
		static void until(List list, Closure closure) {
			list.any { closure.call(it) }
		}
		
		static List collectWithIndex(List list, Closure closure) {
			def i = 0
			return list.collect { closure(it, i++) }
		}
		
		static def findWithIndex(List list, Closure closure) {
			def i = 0
			return list.find { closure(it, i++) }
		}
		
		static def findAllWithIndex(List list, Closure closure) {
			def i = 0
			return list.findAll { closure(it, i++) }
		}
	}
	
	@Test
	void testLoopCategory() {
		
		use(LoopCategory) {
			def x = 0
			
			(1..4).until {
				x++
				return false
			}
			
			assert x == 4
			
			def y = 0
			[1,2,3].until {
				if (it == 3) return true
				
				y++
				
				return false
			}
			
			assert y == 2
			
			def a = ['a','b','c'].collectWithIndex { obj, i -> return i }
			assert a == [0,1,2]
			
			def b = ['a','b','c'].findWithIndex { obj, i -> return obj == 'b' }
			assert b == 'b'
			
			def c = ['a','b','c'].findAllWithIndex { obj, i -> return i < 2 }
			assert c == ['a', 'b']
		}
	}
}