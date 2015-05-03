package com.github.knives.dojo.problem;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountingTriangleTest {

	@Test
	public void test() {
		assertEquals(6, CountingTriangle.count(new int[] {10, 21, 22, 100, 101, 200, 300}));
	}

}
