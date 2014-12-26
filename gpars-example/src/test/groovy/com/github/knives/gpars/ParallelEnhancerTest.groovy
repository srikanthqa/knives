package com.github.knives.gpars

import groovyx.gpars.ParallelEnhancer

import org.junit.Test

class ParallelEnhancerTest {
	
	@Test
	void testEnhanceInstance() {
		def list = [1, 2, 3, 4, 5, 6, 7, 8, 9]
		ParallelEnhancer.enhanceInstance(list)
		println list.collectParallel {it * 2 }
		
		def animals = ['dog', 'ant', 'cat', 'whale']
		ParallelEnhancer.enhanceInstance animals
		println (animals.anyParallel {it ==~ /ant/} ? 'Found an ant' : 'No ants found')
		println (animals.everyParallel {it.contains('a')} ? 'All animals contain a' : 'Some animals can live without an a')	
		
		animals.makeSequential()
		println (animals.anyParallel {it ==~ /ant/} ? 'Found an ant' : 'No ants found')
		println (animals.everyParallel {it.contains('a')} ? 'All animals contain a' : 'Some animals can live without an a')
		
		animals.makeConcurrent()

		animals.asConcurrent {
			println (animals.anyParallel {it ==~ /ant/} ? 'Found an ant' : 'No ants found')
			println (animals.everyParallel {it.contains('a')} ? 'All animals contain a' : 'Some animals can live without an a')
		}
	}
}
