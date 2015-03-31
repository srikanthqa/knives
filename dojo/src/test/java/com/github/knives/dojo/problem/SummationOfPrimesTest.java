package com.github.knives.dojo.problem;

import org.junit.Test;

public class SummationOfPrimesTest {
    @Test
    public void test() {
        SummationOfPrimes primeGod = new SummationOfPrimes();
        // 37550402023
        // 37550402023
        System.out.println(primeGod.sumPrimesLessThan(1000000L));
        System.out.println(primeGod.sumPrimesLessThan(542L));
        System.out.println(primeGod.sumPrimesLessThan(1000000L));

    }
}
