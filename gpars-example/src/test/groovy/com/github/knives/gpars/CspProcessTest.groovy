package com.github.knives.gpars
import groovyx.gpars.group.DefaultPGroup
import groovyx.gpars.scheduler.ResizeablePool

import org.junit.Test

class CspProcessTest {

	@Test
	void testCspProcess() {
		def group = new DefaultPGroup(new ResizeablePool(true))
		
		def t = group.task {
			println "I am a process"
		}
		
		t.join()
	}
}
