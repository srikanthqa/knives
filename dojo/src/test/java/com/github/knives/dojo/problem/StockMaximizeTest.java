package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockMaximizeTest {
    @Test
    public void test() {
        assertEquals(0L, StockMaximize.computeProfit(new int[]{5, 3, 2}));
        assertEquals(197L, StockMaximize.computeProfit(new int[]{1, 2, 100}));
        assertEquals(3L, StockMaximize.computeProfit(new int[] {1, 3, 1, 2}));

    }
}
