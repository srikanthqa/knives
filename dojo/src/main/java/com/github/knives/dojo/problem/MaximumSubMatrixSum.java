package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * left, right are left column and right column of sub-matrix
 * 
 * We compute sum between left column and column for every row 
 * Then run 1D maximum sumarray on that sum array
 */
public interface MaximumSubMatrixSum {
	static long maxSum(long[][] matrix) {
		
		final int numRow = matrix.length;
		final int numCol = matrix[0].length;
		
		long maxSumSoFar = Long.MIN_VALUE;
		
		for (int left = 0; left < numCol; left++) {
			
			long[] sum = new long[numRow];
			
			for (int right = left; right < numCol; right++) {
				
				for (int i = 0; i < numRow; i++) {
					sum[i] += matrix[i][right];
				}
				
				final long maxSumEndHere = MaximumSubarray.maxSumContiguousSubarray(sum);
				System.out.println(Arrays.toString(sum));
				System.out.println(left + " " + right + " " + maxSumEndHere);
				
				maxSumSoFar = Math.max(maxSumSoFar, maxSumEndHere);
			}
		}
		
		return maxSumSoFar;
	}
}
