package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * Triangle Inequality: A + B > C
 */
public interface CountingTriangle {
	static int count(final int[] segments) {
		Arrays.sort(segments);
		
		int count = 0;
		
		for (int i = 0; i < segments.length-2; i++) {
			
			int k = i+2;
			
			for (int j = i+1; j < segments.length; j++) {
	            // Find the rightmost element which is smaller than the sum
	            // of two fixed elements
	            // The important thing to note here is, we use the previous
	            // value of k. If value of arr[i] + arr[j-1] was greater than arr[k],
	            // then arr[i] + arr[j] must be greater than k, because the
	            // array is sorted.
	            while (k < segments.length && segments[i] + segments[j] > segments[k])
	               ++k;
	 
	            // Total number of possible triangles that can be formed
	            // with the two fixed elements is k - j - 1.  The two fixed
	            // elements are arr[i] and arr[j].  All elements between arr[j+1]
	            // to arr[k-1] can form a triangle with arr[i] and arr[j].
	            // One is subtracted from k because k is incremented one extra
	            // in above while loop.
	            // k will always be greater than j. If j becomes equal to k, then
	            // above loop will increment k, because arr[k] + arr[i] is always
	            // greater than arr[k]
	            count += k - j - 1;
			}
		}
		
		return count;
	}
}
