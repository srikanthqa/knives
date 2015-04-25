package com.github.knives.dojo.problem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MarkAndToysTest {
	@Test
	public void testBuy() {
		assertEquals(4, MarkAndToys.buy(50, new long[] { 1, 12, 5, 111, 200, 1000, 10}));
	}
}
