package com.github.knives.dojo.algorithm;

public interface QuickSort {
	static void quickSort(int[] array, int lowIndex, int highIndex) {
		if (highIndex > lowIndex) {
			int partitionIndex = partition(array, lowIndex, highIndex, lowIndex);
			quickSort(array, lowIndex, partitionIndex-1);
			quickSort(array, partitionIndex+1, highIndex);
		}
		
	}
	
	static int partition(int[] array, int lowIndex, int highIndex, int pivotalIndex) {
		final int pivotalValue = array[pivotalIndex];
		// move pivotal to the end
		swap(array, highIndex, pivotalIndex);
		// storeIndex on the left side of pivotal
		int storeIndex = lowIndex;
		// up to and include highIndex-1;
		for (int i = lowIndex; i < highIndex; i++) {
			// move everything less than pivotalValue to storeIndex
			if (array[i] <= pivotalValue) {
				swap(array, storeIndex, i);
				storeIndex++;
			}
		}
		// move pivotal to the storeIndex; knowing that 
		// pivotal value is now reside at highIndex
		swap(array, storeIndex, highIndex);
		
		return storeIndex;
	}
	
	static void swap(int[] array, int i, int j) {
		if (i == j) return;
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
}
