package com.github.knives.dojo.problem;

import java.util.stream.IntStream;

/**
 * Find the greatest product of K consecutive digits in the N digit number.
 *
 * Input Format
 * First line contains T that denotes the number of test cases.
 * First line of each test case will contain two integers N & K.
 * Second line of each test case will contain a N digit integer.
 *
 * Output Format
 * Print the required answer for each test case.
 *
 * Constraints
 * 1≤T≤100
 * 1≤K≤7
 * K≤N≤1000
 */
public interface LargestProductInSeries {
    public static int compute(int[] series, int k) {
        int maxProduct = 0;
        for (int i = 0; i < series.length - k; i++) {
            final int currentProduct = IntStream.range(i, i + k)
                    .map(it -> series[it])
                    .reduce(1, (int l, int r) -> l * r);

            maxProduct = Math.max(maxProduct, currentProduct);
        }

        return maxProduct;
    }
}
