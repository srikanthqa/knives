package com.github.knives.dojo.algorithm;

/**
 * http://www.growingwiththeweb.com/2014/05/counting-sort.html
 * 
 * Non-comparison sort
 * 
 * O(n + k) where n is the number of elements in the array, and k is the max value
 * 
 * Counting sort works by creating an auxiliary array the size of the range of 
 * values, the unsorted values are then placed into the new array using the 
 * value as the index. The auxiliary array is now in sorted order and can be 
 * iterated over to construct the sorted array.
 * 
 * Assumption:
 * + for non-zero minimum you need to shift the min value to zero
 * 
 * Counting sort is an ideal choice when:
 * The list is made up of integers or can be mapped to integers
 * The range of elements is known
*  Most of the elements in the range are present
 * The additional memory usage is not an issue
 */
public interface CountingSort {
	static void sort(int[] array, int maxValue) {
	    int[] buckets = new int[maxValue + 1];

	    for (int i = 0; i < array.length; i++) {
	        buckets[array[i]]++;
	    }

	    int sortedIndex = 0;
	    for (int i = 0; i < buckets.length; i++) {
	        while (buckets[i] > 0) {
	            array[sortedIndex++] = i;
	            buckets[i]--;
	        }
	    }
	    
	}
}
