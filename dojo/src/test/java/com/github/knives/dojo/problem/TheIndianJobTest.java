package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TheIndianJobTest {
    @Test
    public void test() {
        assertTrue(TheIndianJob.execute(1958, new int[]{
                82, 99, 28, 76, 85, 55, 12, 27, 98, 62, 74, 99, 77, 10, 28, 54, 68, 93, 66, 52, 57, 49, 98, 26, 21, 8,
                35, 0, 25, 78, 90, 41, 97, 61, 76, 50, 87, 95, 31, 77, 69, 93, 52, 16, 72, 39, 84, 28, 88, 66, 52, 54,
                27, 51, 39, 83, 34, 5, 97, 72, 57, 45, 21, 93, 59, 34, 10, 41, 17, 71
        }));
    }
}
