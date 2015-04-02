package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * Watson gives Sherlock an array A[1],A[2]...A[N].
 * He asks him to find an integer M between P and Q(both inclusive),
 * such that, min {|A[i]-M|, 1 ≤ i ≤ N} is maximised. If there are multiple solutions, print the smallest one.
 */
public interface SherlockAndMinimax {
    public static int compute(int[] array, final int P, final int Q) {
        Arrays.sort(array);

        //System.out.println(Arrays.toString(array));

        int pIndex  = Arrays.binarySearch(array, P);
        pIndex = pIndex < 0 ? 0 - (pIndex+1) : pIndex;

        int qIndex = Arrays.binarySearch(array, Q);
        qIndex = qIndex < 0 ? 0 - (qIndex+1) : qIndex;

        int maxDistance = Integer.MIN_VALUE;
        int m = P;
        if (m <= array[0]) {
            maxDistance = array[0] - P;
            pIndex +=1;
        }

        for (int i = pIndex; i < qIndex; i++) {
            final int currentDistance = (array[i] - array[i-1]) / 2;
            if (currentDistance > maxDistance) {
                maxDistance = currentDistance;
                m = currentDistance + array[i-1];
            }
        }

        if (qIndex == array.length) {
            if (Q - array[array.length-1] > maxDistance) {
                m = Q;
            }
        } else {
            if (Math.min(Math.abs(array[qIndex] - Q), Math.abs(array[qIndex-1] - Q)) > maxDistance) {
                m = Q;
            }

            final int currentDistance = (array[qIndex] - array[qIndex-1]) / 2;
            final int tmp = currentDistance + array[qIndex-1];
            if (tmp <= Q && currentDistance > maxDistance) {
                m = tmp;
            }
        }

        //System.out.println(m);

        return m;
    }
}
