package com.github.knives.dojo.problem;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaximumSubMatrixSumTest {

	@Test
	public void test() {
		final long maxSum = MaximumSubMatrixSum.maxSum(new long[][] {
			{1, 2, -1, -4, -20},
            {-8, -3, 4, 2, 1},
            {3, 8, 10, 1, 3},
            {-4, -1, 1, 7, -6}	
		});
		
		assertEquals(29, maxSum);
	}

}
