package com.github.knives.dojo.problem;

import java.util.Arrays;

public interface TheIndianJob {

    public static boolean execute(final int G, final int[] robbers) {
        final int totalTime = Arrays.stream(robbers).sum();
        if (totalTime > 2*G) {
            return false;
        }

        final boolean anyGreaterG = Arrays.stream(robbers).anyMatch(it -> it > G);
        if (anyGreaterG) {
            return false;
        }

        final int[][] knapsack = new int[robbers.length+1][G+1];

        for (int i = 0; i <= robbers.length; i++)  {
            for (int w = 0; w <= G; w++) {
                if (i==0 || w==0) {
                    knapsack[i][w] = 0;
                } else if (robbers[i-1] <= w) {
                    // the value is the weight
                    knapsack[i][w] = Math.max(robbers[i - 1] + knapsack[i - 1][w - robbers[i - 1]], knapsack[i - 1][w]);
                } else {
                    knapsack[i][w] = knapsack[i - 1][w];
                }
            }
        }


        final int maxTime = knapsack[robbers.length][G];

        if (totalTime - maxTime > G) {
            return false;
        }

        return true;
    }

}
