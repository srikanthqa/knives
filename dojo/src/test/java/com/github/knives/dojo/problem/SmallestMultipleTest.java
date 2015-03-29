package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SmallestMultipleTest {
    @Test
    public void test() {
        assertEquals(6L, SmallestMultiple.compute(3L));
        assertEquals(2520L, SmallestMultiple.compute(10L));
    }
}
