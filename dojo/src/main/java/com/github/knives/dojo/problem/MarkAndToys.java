package com.github.knives.dojo.problem;

import java.util.Arrays;

public interface MarkAndToys {
	static int buy(long k, long[] prices) {
		Arrays.sort(prices);
		int n = 0;
		for (; n < prices.length; n++) {
			if (k - prices[n] < 0) break;
			k -= prices[n];
		}
		return n;
	}
}
