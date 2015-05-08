package com.github.knives.dojo.algorithm;

public interface CycleDetection {
	/**
	 * @param array each value is the next index 
	 * @return true if there is a cycle in the array
	 */
	static boolean hasCycle(int[] array) {
		if (array.length == 0) return false;
		
		int i1x = 0;
		int i2x = 0;
		
		// i1x is one-step index
		// i2x is two-step index
		do {
			i1x = array[i1x];
			i2x = array[i2x];
			if (i2x != -1) 	i2x = array[i2x];
		} while (i1x != i2x && i1x != -1 && i2x != -1);
		
		return i1x == i2x && i1x != -1 && i2x != -1;
	}
}
