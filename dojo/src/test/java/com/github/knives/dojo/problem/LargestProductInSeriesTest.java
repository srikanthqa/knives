package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LargestProductInSeriesTest {
    @Test
    public void test() {
        assertEquals(3150, LargestProductInSeries.compute(new int[]{3, 6, 7, 5, 3, 5, 6, 2, 9, 1}, 5));
        assertEquals(0, LargestProductInSeries.compute(new int[]{2,7,0,9,3,6,0,6,2,6}, 5));
    }
}
