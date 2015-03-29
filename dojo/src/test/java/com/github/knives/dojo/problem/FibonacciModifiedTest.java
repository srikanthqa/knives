package com.github.knives.dojo.problem;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class FibonacciModifiedTest {
    @Test
    public void test() {
        assertEquals(BigInteger.valueOf(5), FibonacciModified.compute(0, 1, 5));
    }
}
