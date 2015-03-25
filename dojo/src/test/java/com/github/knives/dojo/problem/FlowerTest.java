package com.github.knives.dojo.problem;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class FlowerTest {
    @Test
    public void test() {
        assertEquals(13L, Flower.totalCost(3, Arrays.asList(2L, 5L, 6L)));
        assertEquals(15L, Flower.totalCost(2, Arrays.asList(2L, 5L, 6L)));

    }
}
