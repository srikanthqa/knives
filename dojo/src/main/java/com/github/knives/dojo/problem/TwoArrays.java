package com.github.knives.dojo.problem;

import java.util.Arrays;

public interface TwoArrays {
	static boolean permute(long k, int n, long[] A, long[] B) {
		if (n == 0) {
			return true;
		}
		
		Arrays.sort(A);
		Arrays.sort(B);
		
		for (int i = 0; i < n; i++) {
			final int j = n-i-1;
			
			if (A[i] + B[j] < k) return false;
		}
		
		return true;
	}
	
	static void swap(long[] array, int i, int j) {
		long tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
}
