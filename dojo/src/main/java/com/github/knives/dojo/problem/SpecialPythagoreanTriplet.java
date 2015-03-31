package com.github.knives.dojo.problem;


/**
 * A Pythagorean triplet is a set of three natural numbers, a<b<c, for which,
 * a^2 + b^2 = c^2
 * For example, 3^2 + 4^2 = 9 +1 6 = 25 = 5^2
 * Given N, Check if there exists any Pythagorean triplet for which a+b+c=N
 * Find maximum possible value of abc among all such Pythagorean triplets, If there is no such Pythagorean triplet print −1.
 */
public interface SpecialPythagoreanTriplet {
    public static long compute(long n) {
        long ret = -1;

        for (long a = 1; a < n; a++) {
            for (long b = a+1; b < n; b++) {
                final long c = n - a - b;
                if (c < b) break;
                if (a * a + b * b == c * c) {
                    ret = Math.max(ret, a * b * c);
                }
            }
        }

        return ret;
    }
}
