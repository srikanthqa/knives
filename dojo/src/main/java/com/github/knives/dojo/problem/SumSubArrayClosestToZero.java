package com.github.knives.dojo.problem;

import java.util.Arrays;
import java.util.Comparator;

public interface SumSubArrayClosestToZero {
	static int sum(int[] array) {
		// scan for zero sum subarray of 1 elements ?
		
		final int[] cumulative = new int[array.length];
		
		cumulative[0] = array[0];
		for (int i = 1; i < array.length; i++) {
			cumulative[i] = cumulative[i-1] + array[i];
		}
		
		final Integer[] indices = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			indices[i] = i;
		}
		
		// sorted indices array
		Arrays.sort(indices, Comparator.comparingInt(i -> cumulative[i]));
		System.out.println(Arrays.toString(cumulative));
		System.out.println(Arrays.toString(indices));

		int bestSum = cumulative[indices[0]];
		for (int i = 1; i < array.length; i++) {
			// check cummulative sum as well
			if (Math.abs(bestSum) > Math.abs(cumulative[indices[i]])) {
				bestSum = cumulative[indices[i]];
			}
			
			final int leftIndex = Math.min(indices[i], indices[i-1]);
			final int rightIndex = Math.max(indices[i], indices[i-1]);
			final int curSum = cumulative[rightIndex] - cumulative[leftIndex];
			if (Math.abs(bestSum) > Math.abs(curSum)) {
				bestSum = curSum;
			}
		}
		
		return bestSum;
	}
}
