package com.github.knives.dojo.problem;

public interface CyclicQuadruples {
	static long modulo = 1000000007L;
	
	static long count(long[] L, long[] R) {
		// Solving using inclusion-exclusion approach ?
		// example:
		// a + b + c + d = 100 => C(100,3)
		// when a = b, 2a + c + d = 100 => C(100,2)/2 (for odd/even number)
		// when b = c, a + 2b + d = 100
		// when c = d, a + b + 2c = 100
		// when d = a, b + c + 2d = 100
		return 0;
	}
}
