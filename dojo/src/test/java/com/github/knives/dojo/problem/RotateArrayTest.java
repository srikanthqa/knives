package com.github.knives.dojo.problem;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class RotateArrayTest {
    @Test
    public void testRotateEvenDivide1() {
        final int[] myArray = new int[]{1,2,3,4,5,6};
        RotateArray.rotate(myArray, 2);
        System.out.println(Arrays.toString(myArray));
        assertArrayEquals(new int[]{3, 4, 5, 6, 1, 2}, myArray);
    }

    @Test
    public void testRotateEvenDivide2() {
        final int[] myArray = new int[]{1,2,3,4,5,6};
        RotateArray.rotate(myArray, 3);
        System.out.println(Arrays.toString(myArray));
        assertArrayEquals(new int[]{4, 5, 6, 1, 2, 3}, myArray);
    }

    @Test
    public void testRotateNotEvenDivide1() {
        final int[] myArray = new int[]{1,2,3,4,5,6};
        RotateArray.rotate(myArray, 4);
        System.out.println(Arrays.toString(myArray));
        assertArrayEquals(new int[]{5, 6, 1, 2, 3, 4}, myArray);
    }

    @Test
    public void testRotateNotEvenDivide2() {
        final int[] myArray = new int[]{1,2,3,4,5,6,7};
        RotateArray.rotate(myArray, 4);
        System.out.println(Arrays.toString(myArray));
        assertArrayEquals(new int[]{5, 6, 7, 1, 2, 3, 4}, myArray);
    }
}
