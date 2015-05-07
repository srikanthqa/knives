package com.github.knives.dojo.algorithm;

import java.util.Random;

public interface ReservoirSampling {

    static int[] select(int k, int n) {
        final int[] ret = new int[k];
        int i = 0;
        for (; i < k; i++) ret[i] = i;

        Random random = new Random();
        for (; i < n; i++) {
            int j = random.nextInt(n);
            if (j < k) {
                ret[j] = i;
            }
        }

        return ret;
    }

    static int[] selectOrdered(int k, int n) {
        final int[] ret = new int[k];
        int select = k;
        int remaining = n;
        for (int i = 0; i < n; i++) {
            if (Math.random() < ((double)select)/((double)remaining)) {
                ret[k - select] = i;
                select--;
            }
            remaining--;
        }
        return ret;
    }
}
