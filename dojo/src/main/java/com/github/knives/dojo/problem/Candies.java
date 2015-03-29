package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * Alice is a kindergarden teacher. She wants to give some candies to the children in her class.
 * All the children sit in a line, and each  of them has a rating score according to his or her
 * performance in the class.  Alice wants to give at least 1 candy to each child.
 *
 * If two children sit next to each other, then the one with the higher rating must get more candies.
 * Alice wants to save money, so she needs to minimize the total number of candies.
 */
public interface Candies {
    public static long compute(int[] ratings) {
        long[] candies = new long[ratings.length];

        // everyone has 1 candy
        Arrays.fill(candies, 1);

        // go forward to take care of increment
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i-1]) {
                candies[i] = candies[i-1]+1;
            }
        }

        // go backward to take care of decrement
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i+1]) {
                // max for hitting peak of increment
                candies[i] = Math.max(candies[i], candies[i+1]+1);
            }
        }

        return Arrays.stream(candies).sum();
    }
}
