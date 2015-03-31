package com.github.knives.dojo.problem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RedJohnIsBack {

    final private static long[] ARRANGE_CACHE = new long[] {1, 1, 1, 2, 3, 4, 5, 7, 10, 14, 19, 26,
            36, 50, 69, 95, 131, 181, 250, 345, 476, 657, 907, 1252, 1728, 2385, 3292, 4544, 6272,
            8657, 11949, 16493, 22765, 31422, 43371, 59864, 82629, 114051, 157422, 217286};

    final private ArrayList<Long> knownPrimes = new ArrayList<Long>();
    private long queryPrimeMaxCeiling = 13;

    public RedJohnIsBack() {
        knownPrimes.add(2L);
        knownPrimes.add(3L);
        knownPrimes.add(5L);
        knownPrimes.add(7L);
        knownPrimes.add(11L);
        knownPrimes.add(13L);
    }

    public long compute(int n) {
        return countPrimeLessThan(cacheArrange(n));
    }

    public long cacheArrange(int n) {
        return ARRANGE_CACHE[n-1];
    }

    public long countPrimeLessThan(long n) {
        if (n <= queryPrimeMaxCeiling) {
            long count = 0;
            for (long prime : knownPrimes) {
                if (prime > n) {
                    break;
                }
                count++;
            }

            return count;
        } else {
            // bump the ceiling
            for (; queryPrimeMaxCeiling <= n; queryPrimeMaxCeiling += 2) {
                if (isPrime(queryPrimeMaxCeiling)) {
                    knownPrimes.add(queryPrimeMaxCeiling);
                }
            }

            return knownPrimes.size();
        }
    }

    public boolean isPrime(long candidate) {
        for (final long prime : knownPrimes) {
            if (candidate % prime == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Given area 4 x N
     * And infinite of bricks of 1x4 and 4x1
     *
     * How many way can you arrange bricks in the given area?
     *
     */
    public long computeArrangement(int n) {
        // treat as dash and dot
        long way = 0;
        for (int i = 0; i <= n / 4; i++) {
            long totalDash = i;
            long totalDashAndDot = n - (i * 4) + i;
            way += choose(totalDashAndDot, totalDash);
        }

        return way;
    }

    public long choose(long n, long k) {
        BigInteger combination = BigInteger.ONE;
        for (long i = k+1; i <= n; i++) {
            combination = combination.multiply(BigInteger.valueOf(i));
        }


        for (long i = 1; i <= n-k; i++) {
            combination = combination.divide(BigInteger.valueOf(i));
        }

        return combination.longValue();
    }
}

