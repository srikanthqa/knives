package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinChangeTest {
    @Test
    public void test() {
        //assertEquals(4, CoinChange.compute(new int[]{1, 3, 2}, 4));
        //assertEquals(5, CoinChange.compute(new int[]{2, 5, 3, 6}, 10));

        //assertEquals(96190959, CoinChange.compute(new int[]{5, 37, 8, 39, 33, 17, 22, 32, 13, 7, 10, 35,
        //        40, 2, 43, 49, 46, 19, 41, 1, 12, 11, 28}, 166));

        assertEquals(15685693751L, CoinChange.compute(new int[]{41, 34, 46, 9, 37, 32, 42, 21, 7, 13, 1, 24, 3, 43,
                2, 23, 8, 45, 19, 30, 29, 18, 35, 11}, 250));


    }


}
