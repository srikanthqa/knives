package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LargestPrimeFactorTest {
    @Test
    public void test() {
        assertEquals(5L, LargestPrimeFactor.compute(10L));

        assertEquals(17L, LargestPrimeFactor.compute(17L));

        assertEquals(6857L, LargestPrimeFactor.compute(600851475143L));

        // very large prime
        assertEquals(100000000003L, LargestPrimeFactor.compute(100000000003L));

    }
}
