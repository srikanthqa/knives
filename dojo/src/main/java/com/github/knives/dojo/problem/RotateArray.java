package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * Rotate array without full n space additional memory and still run in O(n)
 */
public interface RotateArray {
    /**
     * @param array
     * @param K always less than array.length
     */
    static void rotate(int[] array, int K) {
        final int N = array.length;
        int tmp;


        /*
        // maybe when N / K
        // we need only move L groups with K sizes
        //final int L = N / K;

        for (int i = 0; i < K; i++) {
            tmp = array[i];

            for (int j = 0; j < L-1; j++) {
                final int curGroupIndex = i + j*K;
                final int nxtGroupIndex = i + (j+1)*K;
                array[curGroupIndex] = array[nxtGroupIndex];
                System.out.println(Arrays.toString(array));
            }

            final int lastGroupIndex = i + (L-1)*K;
            array[lastGroupIndex] = tmp;
            System.out.println(Arrays.toString(array));
        }
        */


        // T is group size rather than K, it could be equal to K when N % K == 0
        final int T = gcd(N, K);

        for (int i = 0; i < T; i++) {
            tmp = array[i];

            // j is the last index
            int j = i;
            while (true) {
                // h is the next index
                int h = j+K;
                if (h >= N) h -= N;
                // rotate back
                if (h == i) break;

                // swap
                array[j] = array[h];

                // store last index
                j = h;

                System.out.println(Arrays.toString(array));
            }

            array[j] = tmp;
            System.out.println(Arrays.toString(array));
        }
    }

    static int gcd(int a, int b) {
        while (a != b) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }

        return a;
    }
}
