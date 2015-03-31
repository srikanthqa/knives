package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LongestIncreasingSequenceTest {
    @Test
    public void test() {
        assertEquals(6, LongestIncreasingSequence.length(new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15}));

        assertEquals(3, LongestIncreasingSequence.length(new int[]{2, 7, 4, 1, 8}));
    }
}
