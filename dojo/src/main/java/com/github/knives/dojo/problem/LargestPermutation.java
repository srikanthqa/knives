package com.github.knives.dojo.problem;

/**
 * You are given an array of N integers which is a permuation of the first N natural numbers. 
 * 
 * You can swap any two elements of the array. You can make at most K swaps. 
 * 
 * What is the largest permutation, in numerical order, you can make?
 * 
 * Almost like insertion sort with k comparison
 * + the fact it is natural N integer you can make it O(n) instead of O(n^2)
 * just by keeping index of each element
 */
public interface LargestPermutation {
	static int[] permute(int[] array, long k) {
		if (k >= array.length) {
			for (int i = 0; i < array.length; i++) {
				array[i] = array.length - i;
			}
			return array;
		} 
		
		final int[] numberToIndex = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			numberToIndex[array[i]-1] = i;
		}
		
		long swapCount = k;
		for (int i = 0; i < array.length; i++) {
			final int current = array[i];
			final int expect = array.length-i;
			
			if (current != expect && swapCount > 0) {
				final int j = numberToIndex[expect-1];

				swap(array, i, j);
				
				// expect goes to i location
				numberToIndex[expect-1] = i;
				
				// current goes to j location
				numberToIndex[current-1] = j;

				swapCount--;
			}
		}
		
		return array;
	}
	
	static void swap(int[] array, int i, int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
}
