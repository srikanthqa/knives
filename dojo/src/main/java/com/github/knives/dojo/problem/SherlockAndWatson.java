package com.github.knives.dojo.problem;

/**
 * John Watson performs an operation called Right Circular Rotation on an integer array [a0,a1,...an−1]. 
 * Right Circular Rotation transforms the array from [a0,a1,...an−1] to [aN−1,a0,...,aN−2].
 *
 * He performs the operation K times and tests Sherlock's ability to identify the element at a particular 
 * position in the array. He asks Q queries. Each query consists of one integer, idx, for which you have 
 * to print the element at index idx in the rotated array, i.e. aidx.
 *
 * Input Format 
 * The first line consists of three integers, N, K, and Q,, separated by a single space. 
 * The next line contains N space-separated integers which indicate the elements of the array A. 
 * Each of the next Q lines contains one integer per line denoting idx.
 *
 * Output Format 
 * For each query, print the value at index idx in the updated array separated by newline.
 */
public interface SherlockAndWatson {
	static int[] rotateAndFind(int[] array, int K, int query[]) {
		final int[] ret = new int[query.length];
		
		for (int i = 0; i < query.length; i++) {
            final int idx = query[i];
            int realIndex = (idx - K) % array.length;
            realIndex = realIndex < 0 ? realIndex+array.length : realIndex;
            ret[i] = array[realIndex];
        }
		
		return ret;
	}
}
