package com.github.knives.dojo.problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MaxMin {
	public static void main(String[] args) throws NumberFormatException,
			IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(in.readLine());
		int K = Integer.parseInt(in.readLine());
		int[] list = new int[N];

		for (int i = 0; i < N; i++)
			list[i] = Integer.parseInt(in.readLine());

		int unfairness = Integer.MAX_VALUE;

		/*
		 * Write your code here, to process numPackets N, numKids K, and the
		 * packets of candies Compute the ideal value for unfairness over here
		 */
		Arrays.sort(list);

		for (int i = K - 1; i < N; i++) {
			final int min = list[i - K + 1];
			final int max = list[i];
			unfairness = Math.min(unfairness, max - min);
		}

		System.out.println(unfairness);
	}
}
