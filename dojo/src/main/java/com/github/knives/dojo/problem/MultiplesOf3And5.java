package com.github.knives.dojo.problem;

/**
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9.
 * The sum of these multiples is 23.
 *
 * Find the sum of all the multiples of 3 or 5 below N.
 *
 * Input Format
 * First line contains T that denotes the number of test cases. This is followed by T lines, each containing an integer, N.
 *
 * Output Format
 * For each test case, print an integer that denotes the sum of all the multiples of 3 or 5 below N.
 *
 * Constraints
 * 1 <= N <= 10^9
 */
public interface MultiplesOf3And5 {

    public static long sum(long n) {
        // number of term dividable by 15
        long n15 = n / 15L;
        // exclude greatest term if greatest term equal to n
        n15 = (n15 * 15L == n) ? n15 - 1: n15;

        // sum of arithetmic sequence = n * (a_1 + a_n) / 2
        // so sum from 0 .. n15)*15L (sequence of dividable by 15)
        // 0 is counted as a term so n15+1
        // then later divided by 2
        final long sum15 = (n15 + 1) * n15 * 15L;

        long n5 = n / 5L;
        // adjust if equal to n
        n5 = (n5 * 5L == n) ? n5 - 1: n5;
        final long sum5 = (n5 + 1) * n5 * 5L;


        long n3 = n / 3L;
        // adjust if equal to n
        n3 = (n3 * 3L == n) ? n3 - 1: n3;
        final long sum3 = (n3 + 1) * n3 * 3L;

        // sum3 + sum5 and exclude double-count sum15
        // divided by 2 so we don't lose precision.
        return (sum3 + sum5 - sum15)/2;
    }
}
