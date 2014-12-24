package com.github.knives.gpars

import groovyx.gpars.GParsPool

import org.junit.Test

class GParsPoolTest {
	
	final static def WORDS_EN = GParsPoolTest.class.getResource("/wordsEn.txt").readLines();
	
	@Test
	void testWithPool() {
		println("testWithPool")
		StopWatch.withTimeRecording {
			GParsPool.withPool {
				println(WORDS_EN.anyParallel {it ==~ /ant/} ? 'Found an ant' : 'No ants found')
				println(WORDS_EN.everyParallel {it.contains('a')} ? 'All animals contain a' : 'Some animals can live without an a')
			}
		}
	}
	
	@Test
	void testWithoutPool() {
		println("testWithoutPool")
		
		StopWatch.withTimeRecording {
			println(WORDS_EN.any {it ==~ /ant/} ? 'Found an ant' : 'No ants found')
			println(WORDS_EN.every {it.contains('a')} ? 'All animals contain a' : 'Some animals can live without an a')
		}
	}
}
