package com.github.knives.dojo.problem;

import java.math.BigInteger;

/**
 * The prime factors of 13195 are 5, 7, 13 and 29.
 * What is the largest prime factor of a given number N?
 *
 * Input Format
 * First line contains T, the number of test cases. This is followed by T lines each containing an integer N.
 *
 * Output Format
 * For each test case, display the largest prime factor of N.
 *
 * Constraints
 * 1≤T≤10
 * 10≤N≤1012
 */
public interface LargestPrimeFactor {
    /**
     * @param n
     * @return greatest prime factor of n
     */
    public static long compute(long n) {
        long gpf = 1;
        for (long i = 2; i <= n ; i++) {
            if (n%i == 0) {
                for ( ; n%i == 0; n /= i);
                gpf = i;
            }

            // lol, cheat!
            if (BigInteger.valueOf(n).isProbablePrime(1000000000)) {
                return n;
            }
        }

        return gpf;
    }
}
