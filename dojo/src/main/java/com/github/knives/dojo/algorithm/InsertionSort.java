package com.github.knives.dojo.algorithm;

public interface InsertionSort {
	static void sort(int[] array) {
		// for each pivotal at index i
		for (int i = 1; i < array.length; i++) {
			// find the index j to insert in increasing order
			for (int j = i; j >= 0 && array[j-1] > array[j]; j--) {
				int tmp = array[j+1];
				array[j+1] = array[j];
				array[j] = tmp;
			}
		}
	}
}
