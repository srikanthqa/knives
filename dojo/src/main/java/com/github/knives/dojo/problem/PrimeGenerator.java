package com.github.knives.dojo.problem;

public interface PrimeGenerator {
    public static long[] compute(int n) {
        final long[] primes = new long[n];

        if (n >= 1) primes[0] = 2;
        if (n >= 2) primes[1] = 3;
        if (n >= 3) primes[2] = 5;
        if (n >= 4) primes[3] = 7;
        if (n >= 5) primes[4] = 11;
        if (n >= 6) primes[5] = 13;

        if (n >= 7) {
            long current = primes[5];
            int i = 6;
            while (i < n) {
                current++;
                boolean foundPrime = isPrime(current, primes, i);
                if (foundPrime) {
                    primes[i] = current;
                    i++;
                }
            }
        }

        return primes;
    }

    public static boolean isPrime(long candidate, long[] primes, int len) {
        for (int j = 0; j < len; j++) {
            if (candidate % primes[j] == 0) {
                return false;
            }
        }
        return true;
    }
}
