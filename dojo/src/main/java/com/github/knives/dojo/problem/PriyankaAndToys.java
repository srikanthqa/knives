package com.github.knives.dojo.problem;

import java.util.Arrays;

public interface PriyankaAndToys {
	static int buyToys(int[] weights) {
		Arrays.sort(weights);
		
		int toys = 1;
		int currentMaxWeight = weights[weights.length -1];
		for (int i = weights.length -1; i >= 0; i--) {
			if (!(currentMaxWeight - 4 <= weights[i] && weights[i] <= currentMaxWeight)) {
				toys += 1;
				currentMaxWeight = weights[i];
			}
		}
		
		return toys;
	}
}
