package com.github.knives.gpars

import static groovyx.gpars.GParsPool.runForkJoin
import static groovyx.gpars.GParsPool.withPool

import org.junit.Test

class MergesortTest {
	def split(List<Integer> list) {
		int listSize = list.size()
		int middleIndex = listSize / 2
		def list1 = list[0..<middleIndex]
		def list2 = list[middleIndex..listSize - 1]
		return [list1, list2]
	}
	
	/**
	 * Merges two sorted lists into one
	 */
	List<Integer> merge(List<Integer> a, List<Integer> b) {
		int i = 0, j = 0
		final int newSize = a.size() + b.size()
		List<Integer> result = new ArrayList<Integer>(newSize)
	
		while ((i < a.size()) && (j < b.size())) {
			if (a[i] <= b[j]) result << a[i++]
			else result << b[j++]
		}
	
		if (i < a.size()) result.addAll(a[i..-1])
		else result.addAll(b[j..-1])
		return result
	}
	
	@Test
	void testMergeSort() {
		final def numbers = [1, 5, 2, 4, 3, 8, 6, 7, 3, 4, 5, 2, 2, 9, 8, 7, 6, 7, 8, 1, 4, 1, 7, 5, 8, 2, 3, 9, 5, 7, 4, 3]
		
		withPool(3) {  //feel free to experiment with the number of fork/join threads in the pool
			println """Sorted numbers: ${
		        runForkJoin(numbers) {nums ->
		            println "Thread ${Thread.currentThread().name[-1]}: Sorting $nums"
		            switch (nums.size()) {
		                case 0..1:
		                    return nums                                   //store own result
		                case 2:
		                    if (nums[0] <= nums[1]) return nums     //store own result
		                    else return nums[-1..0]                       //store own result
		                default:
		                    def splitList = split(nums)
		                    [splitList[0], splitList[1]].each {forkOffChild it}  //fork a child task
		                    return merge(* childrenResults)      //use results of children tasks to calculate and store own result
		            }
		        }
		    }"""
		}
	}
}
