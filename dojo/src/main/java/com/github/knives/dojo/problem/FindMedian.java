package com.github.knives.dojo.problem;


/**
 * FindMedian by using partition
 * 
 * You don't sort everything unless you have to
 */
public interface FindMedian {
	static int median(int[] array) {
		final int medianIndex = array.length / 2;
		
		int lowIndex = 0;
		int highIndex = array.length-1;
		
		while (highIndex > lowIndex) {
			int partitionIndex = partition(array, lowIndex, highIndex);
			if (partitionIndex == medianIndex) break;

			if (partitionIndex > medianIndex) {
				highIndex = partitionIndex-1;
			} else {
				lowIndex = partitionIndex+1;
			}
		}
		
		return array[medianIndex];
	}
	
	static int partition(int[] array, int lowIndex, int highIndex) {
		// assume pivotal is the last
		final int pivotalValue = array[highIndex];
		
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
