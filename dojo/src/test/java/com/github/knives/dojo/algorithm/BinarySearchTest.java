package com.github.knives.dojo.algorithm;

import com.github.knives.dojo.algorithm.BinarySearch;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinarySearchTest {
    @Test
    public void testOneElement() {
        assertEquals(0, BinarySearch.lookup(new int[]{1}, 1));
    }

    @Test
    public void testTwoElement() {
        assertEquals(0, BinarySearch.lookup(new int[]{1,2}, 1));
    }

    @Test
    public void testTwoElement1() {
        assertEquals(1, BinarySearch.lookup(new int[]{1,2}, 2));
    }

    @Test
    public void testThreeElement() {
        assertEquals(0, BinarySearch.lookup(new int[]{1,2,3}, 1));
    }

    @Test
    public void testThreeElement2() {
        assertEquals(2, BinarySearch.lookup(new int[]{1,2,3}, 3));
    }

    @Test
    public void testThreeElement3() {
        assertEquals(1, BinarySearch.lookup(new int[]{1,2,3}, 2));
    }

    @Test
    public void testNotFoundZeroElement() {
        assertEquals(-1, BinarySearch.lookup(new int[]{}, 1));
    }

    @Test
    public void testNotFound() {
        assertEquals(-1, BinarySearch.lookup(new int[]{2}, 1));
    }
}
