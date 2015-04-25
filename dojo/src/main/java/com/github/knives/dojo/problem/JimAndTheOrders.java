package com.github.knives.dojo.problem;

import java.util.Arrays;
import java.util.Comparator;

public interface JimAndTheOrders {
	static Integer[] order(int n, int[] t, int[] d) {
		final Integer[] fan = new Integer[n];
		final Integer[] finishTime = new Integer[n];

		for (int i = 1; i<= n; i++) {
			fan[i-1] = i;
			finishTime[i-1] = t[i-1] + d[i-1];
		}
		
		Arrays.sort(fan, Comparator.comparingInt(it -> finishTime[it-1]));
		return fan;
	}
}
