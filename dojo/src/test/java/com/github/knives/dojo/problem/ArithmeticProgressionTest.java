package com.github.knives.dojo.problem;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ArithmeticProgressionTest {
    @Test
    public void test() {
        assertEquals(5L, ArithmeticProgression.findMissingTerms(Arrays.asList(1L, 3L, 7L)));

        assertEquals(9L, ArithmeticProgression.findMissingTerms(Arrays.asList(1L, 5L, 13L, 17L)));

        assertEquals(13L, ArithmeticProgression.findMissingTerms(Arrays.asList(1L, 5L, 9L, 17L)));

        assertEquals(-1L, ArithmeticProgression.findMissingTerms(Arrays.asList(1L, 3L, 5L, 7L)));

        assertEquals(-1L, ArithmeticProgression.findMissingTerms(Arrays.asList(1L, 3L, 5L, 7L, 9L)));

        // negative case
        assertEquals(-5L, ArithmeticProgression.findMissingTerms(Arrays.asList(-1L, -3L, -7L)));

        assertEquals(-9L, ArithmeticProgression.findMissingTerms(Arrays.asList(-1L, -5L, -13L, -17L)));

        assertEquals(-13L, ArithmeticProgression.findMissingTerms(Arrays.asList(-1L, -5L, -9L, -17L)));

        assertEquals(1L, ArithmeticProgression.findMissingTerms(Arrays.asList(-1L, -3L, -5L, -7L)));

        assertEquals(1L, ArithmeticProgression.findMissingTerms(Arrays.asList(-1L, -3L, -5L, -7L, -9L)));
    }

}
