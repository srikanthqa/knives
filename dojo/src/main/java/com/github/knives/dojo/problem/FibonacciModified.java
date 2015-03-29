package com.github.knives.dojo.problem;

import java.math.BigInteger;

/**
 * A series is defined in the following manner:
 *
 * Given the nth and (n+1)th terms, the (n+2)th can be computed by the following relation
 * Tn+2 = (Tn+1)2 + Tn
 *
 * So, if the first two terms of the series are 0 and 1:
 * the third term = 12 + 0 = 1
 * fourth term = 12 + 1 = 2
 * fifth term = 22 + 1 = 5
 * ... And so on.
 *
 * Given three integers A, B and N, such that the first two terms of the series (1st and 2nd terms) are A and B respectively,
 * compute the Nth term of the series.
 *
 * Input Format
 *
 * You are given three space separated integers A, B and N on one line.
 *
 * Input Constraints
 * 0 <= A,B <= 2
 * 3 <= N <= 20
 *
 * Output Format
 *
 * One integer.
 * This integer is the Nth term of the given series when the first two terms are A and B respectively.
 *
 * Note
 *
 * Some output may even exceed the range of 64 bit integer.
 */
public interface FibonacciModified {
    public static BigInteger compute(int a, int b, int n) {
        final BigInteger[] terms = new BigInteger[2];
        terms[0] = BigInteger.valueOf(a);
        terms[1] = BigInteger.valueOf(b);

        int i = 2;
        while (i < n) {
            final BigInteger tn = terms[(i-2)%2];
            final BigInteger tn1 = terms[(i-1) % 2];
            terms[i%2] = tn1.multiply(tn1).add(tn);
            i++;
        }

        return terms[(n-1) % 2];
    }
}
