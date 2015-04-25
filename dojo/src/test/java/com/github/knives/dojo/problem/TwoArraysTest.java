package com.github.knives.dojo.problem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TwoArraysTest {
	@Test
	public void testPermute() {
		assertTrue(TwoArrays.permute(10, 3, new long[] {2, 1, 3}, new long[] {7, 8, 9}));
		
		assertFalse(TwoArrays.permute(5, 4, new long[] {1, 2, 2, 1}, new long[] {3, 3, 3, 4}));
	}
}
