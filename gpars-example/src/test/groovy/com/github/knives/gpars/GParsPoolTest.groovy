package com.github.knives.gpars

import groovyx.gpars.GParsPool

import org.junit.Test

class GParsPoolTest {
	
	final static def NUMBER_THREADS = Runtime.getRuntime().availableProcessors() * 2;
	final static def WORDS_EN = GParsPoolTest.class.getResource("/wordsEn.txt").readLines();
	static {
		println ("Number of words: " + WORDS_EN.size())
	}
	
	@Test
	void testAnyParallel() {
		StopWatch.time("testAnyParallel") {
			GParsPool.withPool(NUMBER_THREADS) {
				println(WORDS_EN.anyParallel {it ==~ /ant/} ? 'Found an ant' : 'No ants found')
			}
		}
	}
	
	@Test
	void testAnySequential() {
		StopWatch.time("testAnySequential") {
			println(WORDS_EN.any {it ==~ /ant/} ? 'Found an ant' : 'No ants found')
		}
	}
	
	@Test
	void testEveryParallel() {
		StopWatch.time("testEveryParallel") {
			GParsPool.withPool(NUMBER_THREADS) {
				println(WORDS_EN.everyParallel {it.contains('a')} ? 'All animals contain a' : 'Some animals can live without an a')
			}
		}
	}
	
	@Test
	void testEverySequential() {
		StopWatch.time("testEverySequential") {
			println(WORDS_EN.every {it.contains('a')} ? 'All animals contain a' : 'Some animals can live without an a')
		}
	}
	
	@Test
	void testInjectParallel() {
		StopWatch.time("testInjectParallel") {
			GParsPool.withPool(NUMBER_THREADS) {
				// inject parallel does not work with inject
				// injectParallel is a purely reduce function, not a fold function
				println((WORDS_EN.collectParallel { a -> a.size() }).injectParallel { a, b -> a + b })
			}
		}
	}
	
	@Test
	void testInjectSequential() {
		StopWatch.time("testInjectSequential") {
			println(WORDS_EN.inject(0) { acc, val -> acc + val.size() })
		}
	}
	
	@Test
	void testCallAsync() {
		GParsPool.withPool {
			println({it * 2}.call(3))
			println({it * 2}.callAsync(3).get())
		}
	}
	
	@Test
	void testRunForkJoin() {
		GParsPool.withPool() {
			println """Number of files: ${GParsPool.runForkJoin(new File("./src")) {file ->
		            long count = 0
		            file.eachFile {
		                if (it.isDirectory()) {
		                    println "Forking a child task for $it"
		                    forkOffChild(it)           //fork a child task
		                } else {
		                    count++
		                }
		            }
		            return count + (childrenResults.sum(0))
		            //use results of children tasks to calculate and store own result
		        }
			}"""
		}
	}
}
