package com.github.knives.gpars

import groovyx.gpars.group.DefaultPGroup

import org.junit.Test

class ReactiveActorTest {
	
	/**
	 * When a reactive actor receives a message, the supplied block of code, 
	 * which makes up the reactive actor's body, is run with the message as a parameter. 
	 * The result returned from the code is sent in reply.
	 */
	@Test
	void testReactiveActor() {
		final def group = new DefaultPGroup()
		final def doubler = group.reactor {
			2 * it
		}
		
		group.actor {
			println 'Double of 10 = ' + doubler.sendAndWait(10)
		}
		
		group.actor {
			println 'Double of 20 = ' + doubler.sendAndWait(20)
		}
		
		group.actor {
			println 'Double of 30 = ' + doubler.sendAndWait(30)
		}
		
		for(i in (1..10)) {
			println "Double of $i = ${doubler.sendAndWait(i)}"
		}
		
		doubler.stop()
		doubler.join()
	}
}
