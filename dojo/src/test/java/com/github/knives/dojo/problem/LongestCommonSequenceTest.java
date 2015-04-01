package com.github.knives.dojo.problem;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class LongestCommonSequenceTest {
    @Test
    public void test() {

        assertArrayEquals(new int[]{1, 2, 3}, LongestCommonSequence.compute(
                new int[]{1, 2, 3, 4, 1},
                new int[]{3, 4, 1, 2, 1, 3}
        ));
    }
}
