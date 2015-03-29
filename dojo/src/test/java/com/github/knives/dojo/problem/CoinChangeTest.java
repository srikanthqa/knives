package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinChangeTest {
    @Test
    public void test() {
        assertEquals(4, CoinChange.compute(new int[]{1, 3, 2}, 4));
    }


}
