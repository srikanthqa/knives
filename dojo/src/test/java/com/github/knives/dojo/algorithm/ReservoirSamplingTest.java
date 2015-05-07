package com.github.knives.dojo.algorithm;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ReservoirSamplingTest {
    @Test
    public void testSelect() {
        final int[] ret = ReservoirSampling.select(4, 10);
        assertEquals(4, ret.length);
        System.out.println(Arrays.toString(ret));
    }

    @Test
    public void testSelectOrder() {
        final int[] ret = ReservoirSampling.selectOrdered(4, 10);
        assertEquals(4, ret.length);
        System.out.println(Arrays.toString(ret));
    }
}
