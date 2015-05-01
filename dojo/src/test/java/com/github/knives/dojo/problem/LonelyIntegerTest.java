package com.github.knives.dojo.problem;

import static org.junit.Assert.*;

import org.junit.Test;

public class LonelyIntegerTest {

	@Test
	public void test() {
		assertEquals(2, LonelyInteger.find(new int[] { 0, 0, 1, 1, 2}));
		
		// when lonely int is 0 ?
		assertEquals(0, LonelyInteger.find(new int[] { 0, 1, 1, 2, 2}));

		assertEquals(1, LonelyInteger.find(new int[] { 1}));
	}

}
