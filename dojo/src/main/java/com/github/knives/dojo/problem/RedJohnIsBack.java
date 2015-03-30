package com.github.knives.dojo.problem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface RedJohnIsBack {

    public static long compute(int n) {
        return countPrimeLessThan(computeArrangement(n));
    }


    public static long countPrimeLessThan(long n) {
        if (n < 2L) return 0;
        if (n < 3L) return 1;
        if (n < 5L) return 2;
        if (n < 7L) return 3;
        if (n < 11L) return 4;
        if (n < 13L) return 5;
        if (n == 13L) return 6;

        final ArrayList<Long> primes = new ArrayList<Long>(10);
        primes.add(2L);
        primes.add(3L);
        primes.add(5L);
        primes.add(7L);
        primes.add(11L);
        primes.add(13L);

        for (long i = 15; i <= n; i += 2) {
            if (isPrime(i, primes)) {
                primes.add(i);
            }
        }

        System.out.println(primes);

        return primes.size();
    }

    public static boolean isPrime(long candidate, List<Long> knownPrimes) {
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
    public static long computeArrangement(int n) {
        // treat as dash and dot
        long way = 0;
        for (int i = 0; i <= n / 4; i++) {
            long totalDash = i;
            long totalDashAndDot = n - (i * 4) + i;
            way += choose(totalDashAndDot, totalDash);
        }

        return way;
    }

    public static long choose(long n, long k) {
        BigInteger combination = BigInteger.ONE;
        for (long i = k+1; i <= n; i++) {
            //System.out.println(n + " choose " + k + " = " + combination);
            combination = combination.multiply(BigInteger.valueOf(i));
            //combination *= i;
        }

        //System.out.println(n + " choose " + k + " = " + combination);

        for (long i = 1; i <= n-k; i++) {
            combination = combination.divide(BigInteger.valueOf(i));
            //combination /= i;
        }

        //System.out.println(n + " choose " + k + " = " + combination);

        return combination.longValue();
    }
}

