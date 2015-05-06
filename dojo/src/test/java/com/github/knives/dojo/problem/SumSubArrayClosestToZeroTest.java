package com.github.knives.dojo.problem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SumSubArrayClosestToZeroTest {
	@Test
	public void testSum() {
		assertEquals(0, SumSubArrayClosestToZero.sum(new int[] {2, 10, -15, 5, 7, -20, -30}));
		assertEquals(0, SumSubArrayClosestToZero.sum(new int[] {10, -15, 5}));
		assertEquals(0, SumSubArrayClosestToZero.sum(new int[] {0, 10, -15, 5}));
		assertEquals(0, SumSubArrayClosestToZero.sum(new int[] {10, -15, 6, 0}));

		assertEquals(2, SumSubArrayClosestToZero.sum(new int[] {10, -15, 2}));

	}
}
