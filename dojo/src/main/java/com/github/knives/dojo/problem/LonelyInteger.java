package com.github.knives.dojo.problem;

/**
 * There are N integers in an array A. All but one integer occur in pairs. 
 * Your task is to find the number that occurs only once.
 */
public interface LonelyInteger {
	static int find(int[] array) {
		// sort could work
		// counting sort is O(n) given the problem constraint on input
		// sort in general O(n log n)
		// however, xor is better strategy
		// xor logic: same => 0, difference = 1
		// when xor with 0, difference = identity
		// if the 0 is the lonely integer, it still is correct
		int xor = 0;
		for (int i = 0; i < array.length; i++) {
			xor ^= array[i];
		}
		return xor;
	}
}
