package com.github.knives.dojo.problem;

public interface SherlockAndPairs {
	// using counting sort
	static long countPair(long[] array) {
        final long[] buckets = new long[1000001];
        for (int i = 0; i < array.length; i++) {
            buckets[i]++; 
        }
        
        long numPair = 0;
        for (int i = 0; i < 1000001; i++) {
            if (buckets[i] > 1) {
                numPair += buckets[i] * (buckets[i] - 1);
            }
        }
        
        return numPair;
	}
}
