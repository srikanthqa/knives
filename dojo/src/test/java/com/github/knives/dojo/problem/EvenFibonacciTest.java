package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EvenFibonacciTest {
    @Test
    public void test() {
        assertEquals(10L, EvenFibonacci.sum(10L));
        assertEquals(44L, EvenFibonacci.sum(100L));
    }
}
