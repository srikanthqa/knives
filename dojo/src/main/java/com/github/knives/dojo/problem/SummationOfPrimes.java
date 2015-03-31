package com.github.knives.dojo.problem;

import java.math.BigInteger;
import java.util.*;

public class SummationOfPrimes {
    final private ArrayList<Long> knownPrimes = new ArrayList<Long>(10000);
    final private Map<Long, Long> primesToSum = new HashMap<Long, Long>(10000);

    private long queryPrimeMaxCeiling = 541L;

    public SummationOfPrimes() {
        knownPrimes.add(2L);
        knownPrimes.add(3L);
        knownPrimes.add(5L);
        knownPrimes.add(7L);
        knownPrimes.add(11L);
        knownPrimes.add(13L);
        knownPrimes.add(17L);
        knownPrimes.add(19L);
        knownPrimes.add(23L);
        knownPrimes.add(29L);
        knownPrimes.add(31L);
        knownPrimes.add(37L);
        knownPrimes.add(41L);
        knownPrimes.add(43L);
        knownPrimes.add(47L);
        knownPrimes.add(53L);
        knownPrimes.add(59L);
        knownPrimes.add(61L);
        knownPrimes.add(67L);
        knownPrimes.add(71L);
        knownPrimes.add(73L);
        knownPrimes.add(79L);
        knownPrimes.add(83L);
        knownPrimes.add(89L);
        knownPrimes.add(97L);
        knownPrimes.add(101L);
        knownPrimes.add(103L);
        knownPrimes.add(107L);
        knownPrimes.add(109L);
        knownPrimes.add(113L);
        knownPrimes.add(127L);
        knownPrimes.add(131L);
        knownPrimes.add(137L);
        knownPrimes.add(139L);
        knownPrimes.add(149L);
        knownPrimes.add(151L);
        knownPrimes.add(157L);
        knownPrimes.add(163L);
        knownPrimes.add(167L);
        knownPrimes.add(173L);
        knownPrimes.add(179L);
        knownPrimes.add(181L);
        knownPrimes.add(191L);
        knownPrimes.add(193L);
        knownPrimes.add(197L);
        knownPrimes.add(199L);
        knownPrimes.add(211L);
        knownPrimes.add(223L);
        knownPrimes.add(227L);
        knownPrimes.add(229L);
        knownPrimes.add(233L);
        knownPrimes.add(239L);
        knownPrimes.add(241L);
        knownPrimes.add(251L);
        knownPrimes.add(257L);
        knownPrimes.add(263L);
        knownPrimes.add(269L);
        knownPrimes.add(271L);
        knownPrimes.add(277L);
        knownPrimes.add(281L);
        knownPrimes.add(283L);
        knownPrimes.add(293L);
        knownPrimes.add(307L);
        knownPrimes.add(311L);
        knownPrimes.add(313L);
        knownPrimes.add(317L);
        knownPrimes.add(331L);
        knownPrimes.add(337L);
        knownPrimes.add(347L);
        knownPrimes.add(349L);
        knownPrimes.add(353L);
        knownPrimes.add(359L);
        knownPrimes.add(367L);
        knownPrimes.add(373L);
        knownPrimes.add(379L);
        knownPrimes.add(383L);
        knownPrimes.add(389L);
        knownPrimes.add(397L);
        knownPrimes.add(401L);
        knownPrimes.add(409L);
        knownPrimes.add(419L);
        knownPrimes.add(421L);
        knownPrimes.add(431L);
        knownPrimes.add(433L);
        knownPrimes.add(439L);
        knownPrimes.add(443L);
        knownPrimes.add(449L);
        knownPrimes.add(457L);
        knownPrimes.add(461L);
        knownPrimes.add(463L);
        knownPrimes.add(467L);
        knownPrimes.add(479L);
        knownPrimes.add(487L);
        knownPrimes.add(491L);
        knownPrimes.add(499L);
        knownPrimes.add(503L);
        knownPrimes.add(509L);
        knownPrimes.add(521L);
        knownPrimes.add(523L);
        knownPrimes.add(541L);

        long sum = 0;
        for (long prime : knownPrimes) {
            sum += prime;
            primesToSum.put(prime, sum);
        }
    }

    public long sumPrimesLessThan(long n) {
        if (n <= queryPrimeMaxCeiling) {
            int index = Collections.binarySearch(knownPrimes, n);
            if (index < 0) index = Math.abs(index+1);
            if (index == -1) return 0;
            if (index == knownPrimes.size() || knownPrimes.get(index) > n) index -= 1;
            long ceilingPrime = knownPrimes.get(index);
            return primesToSum.get(ceilingPrime);
        } else {
            // bump the ceiling
            for (queryPrimeMaxCeiling += 2; queryPrimeMaxCeiling <= n; queryPrimeMaxCeiling += 2) {
                if (isPrime(queryPrimeMaxCeiling)) {
                    primesToSum.put(queryPrimeMaxCeiling, primesToSum.get(knownPrimes.get(knownPrimes.size()-1)) + queryPrimeMaxCeiling);
                    knownPrimes.add(queryPrimeMaxCeiling);

                }
            }

            //System.out.println(knownPrimes.get(knownPrimes.size()-1));
            for (final long prime : knownPrimes) {
                System.out.println("cache.put(" + prime + ", " + primesToSum.get(prime) + ");");
            }



            return primesToSum.get(knownPrimes.get(knownPrimes.size()-1));
        }
    }

    public boolean isPrime(long candidate) {
        //return BigInteger.valueOf(candidate).isProbablePrime(50);
        for (final long prime : knownPrimes) {
            if (candidate % prime == 0) {
                return false;
            }
        }
        return true;
    }
}
