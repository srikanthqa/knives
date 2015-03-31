package com.github.knives.dojo.problem;

import java.util.Arrays;

public interface LongestIncreasingSequence {
    /**
     * http://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
     *
     * 1. If A[i] is smallest among all end candidates of active lists, we will start new active list of length 1.
     *
     * 2. If A[i] is largest among all end candidates of active lists, we will clone the largest active list,
     * and extend it by A[i].
     *
     * 3. If A[i] is in between, we will find a list with largest end element that is smaller than A[i].
     * Clone and extend this list by A[i]. We will discard all other lists of same length as that of this modified list.
     */
    public static int length(int[] sequence) {
        final int[] tailIndices = new int[sequence.length];

        // it will always point to empty location
        int len = 1;

        for( int i = 1; i < sequence.length; i++ ) {
            if( sequence[i] < sequence[tailIndices[0]] ) {
                // new smallest value
                tailIndices[0] = i;
            } else if( sequence[i] > sequence[tailIndices[len-1]] ) {
                // A[i] wants to extend largest subsequence
                tailIndices[len++] = i;
            } else {
                // A[i] wants to be a potential condidate of future subsequence
                // It will replace ceil value in tailIndices
                int pos = binarySearch(sequence, tailIndices, -1, len - 1, sequence[i]);

                tailIndices[pos] = i;
            }
        }

        return len;
    }

    public static int binarySearch(int[] sequence, int[] tailIndices, int l, int r, int key) {
        int m;

        while( r - l > 1 ) {
            m = l + (r - l)/2;
            if( sequence[tailIndices[m]] >= key )
                r = m;
            else
                l = m;
        }

        return r;
    }
}
