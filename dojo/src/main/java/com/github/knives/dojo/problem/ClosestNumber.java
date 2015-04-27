package com.github.knives.dojo.problem;

import java.util.Arrays;

public interface ClosestNumber {
	static int[] getPairs(int[] array) {
		Arrays.sort(array);
		
		int minDiff = array[1] - array[0];
		int pairCount = 2;
		
		for (int i = 2; i < array.length; i++) {
			final int currentDiff = array[i] - array[i-1];
			if (currentDiff == minDiff) {
				pairCount += 2;
			} else if (currentDiff < minDiff) {
				pairCount = 2;
				minDiff = currentDiff;
			}
		}
		
		final int[] pairs = new int[pairCount];
		int pairIndex = 0;
		for (int i = 1; i < array.length; i++) {
			final int currentDiff = array[i] - array[i-1];
			if (currentDiff == minDiff) {
				pairs[pairIndex++] = array[i-1];
				pairs[pairIndex++] = array[i];
			}
		}
		
		return pairs;
	}
}
