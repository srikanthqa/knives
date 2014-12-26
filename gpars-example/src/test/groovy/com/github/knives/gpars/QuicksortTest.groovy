package com.github.knives.gpars

import groovyx.gpars.GParsPool

import org.junit.Test

class QuicksortTest {
	
	@Test
	void testQuickSort() {
		println(quicksort([3,5,10,1,2,-1,-10]))
	}
	
	def quicksort(numbers) {
		GParsPool.withPool {
			GParsPool.runForkJoin(0, numbers) {index, list ->
				def groups = list.groupBy {it <=> list[list.size().intdiv(2)]}
				if ((list.size() < 2) || (groups.size() == 1)) {
					return [index: index, list: list.clone()]
				}
				(-1..1).each {forkOffChild(it, groups[it] ?: [])}
				return [index: index, list: childrenResults.sort {it.index}.sum {it.list}]
			}.list
		}
	}
}
