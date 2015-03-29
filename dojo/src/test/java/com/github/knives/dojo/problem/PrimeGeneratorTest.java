package com.github.knives.dojo.problem;

import org.junit.Test;

import java.util.Arrays;

public class PrimeGeneratorTest {
    @Test
    public void test() {
        System.out.println(Arrays.toString(PrimeGenerator.compute(10000)));
    }
}
