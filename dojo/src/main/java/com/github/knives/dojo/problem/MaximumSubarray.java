package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * Given an array A={a1,a2,…,aN} of N elements, find the maximum possible sum of a
 *
 * Contiguous subarray
 * Non-contiguous (not necessarily contiguous) subarray.
 *
 * Empty subarrays/subsequences should not be considered.
 */
public interface MaximumSubarray {
    /**
     * Kadane algorithm
     */
    public static long maxSumContiguousSubarray(long[] array) {
        boolean isTherePositive = Arrays.stream(array).anyMatch(it -> it > 0);
        if (!isTherePositive) {
            return Arrays.stream(array).max().getAsLong();
        }

        long maxSumSoFar, maxSumEndingHere;

        maxSumSoFar = maxSumEndingHere = array[0];

        for (int i =  1; i < array.length; i++) {
            maxSumEndingHere = Math.max(0, maxSumEndingHere + array[i]);
            maxSumSoFar = Math.max(maxSumSoFar, maxSumEndingHere);
        }

        return maxSumSoFar;
    }

    public static long maxSumNonContiguousSubarray(long[] array) {
        boolean isTherePositive = Arrays.stream(array).anyMatch(it -> it > 0);

        if (isTherePositive) {
            return Arrays.stream(array)
                    .filter(it -> it > 0)
                    .sum();
        } else {
            return Arrays.stream(array).max().getAsLong();
        }
    }
}
