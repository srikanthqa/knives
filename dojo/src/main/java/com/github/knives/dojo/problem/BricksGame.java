package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * You and your friend decide to play a game using a stack consisting of N bricks.
 * In this game, you can alternatively remove 1, 2 or 3 bricks from the top, and
 * the numbers etched on the removed bricks are added to your score. You have to play
 * so that you obtain the maximum possible score. It is given that your friend will
 * also play optimally and you make the first move.
 *
 * Input Format
 * First line will contain an integer T i.e. number of test cases. There will be two lines corresponding
 * to each test case: first line will contain a number N i.e. number of elements in the stack and next line
 * will contain N numbers i.e. numbers etched on bricks from top to bottom.
 *
 * Output Format
 * For each test case, print a single line containing your maximum score.
 *
 * Constraints
 * 1 ≤ T ≤ 5
 * 1 ≤ N ≤ 10^5
 * 0 ≤ each number on brick ≤ 10^9
 */
public interface BricksGame {
    public static long sum(long[] stack) {
        final long[] sum = new long[stack.length];
        final int[] indices = new int[stack.length];

        Arrays.fill(sum, -1);

        compute(stack, 0, sum, indices);

        System.out.println(Arrays.toString(sum));
        System.out.println(Arrays.toString(indices));

        return sum[0];
    }

    /**
     * Compute sum, and indices at h
     *
     * h = stack current top index
     *
     * indices[h] = next index after taking brick from h to h+i for h+i < indices[h] using optional strategy
     *
     * sum[h] = sum when you play first for current stack with top index at h
     */
    public static void compute(long[] stack, int h, long[] sum, int[] indices) {
        if (h >= stack.length) {
            return;
        }

        // cache!!! don't recompute
        if (sum[h] != -1) {
            return;
        }

        // for the last 3 bricks, take all bricks as optimal strategy
        // i.e. indices[h] = stack.length
        if (h == stack.length-1) {
            sum[h] = stack[h];
            indices[h] = stack.length;
            return;
        }

        if (h == stack.length-2) {
            sum[h] = stack[h] + stack[h+1];
            indices[h] = stack.length;
            return;
        }

        if (h == stack.length-3) {
            sum[h] = stack[h] + stack[h+1] + stack[h+2];
            indices[h] = stack.length;
            return;
        }

        // compute sum[h+i], and indices[h+i] for i = 1..3
        // this is to compute (other player or u play) with stack at top index h+1, h+2, h+3
        compute(stack, h + 1, sum, indices);
        compute(stack, h + 2, sum, indices);
        compute(stack, h + 3, sum, indices);

        // here is the optimal strategy

        // you have to pick at least 1 brick
        // suppose after pick 1 pick and ur next turn there is no brick
        if (indices[h+1] >= stack.length) {
            // sum = current brick score
            sum[h] = stack[h];
            indices[h] = h+1;
        } else {
            // otherwise
            // sum = current brick score + sum at index after next turn
            sum[h] = stack[h] + sum[indices[h+1]];
            indices[h] = h+1;
        }

        if (indices[h+2] >= stack.length) {
            long tmp = stack[h] + stack[h+1];
            if (tmp > sum[h]) {
                sum[h] = stack[h] + stack[h+1];
                indices[h] = h+2;
            }
        } else {
            long tmp = stack[h] + stack[h+1] + sum[indices[h+2]];
            if (tmp > sum[h]) {
                sum[h] = tmp;
                indices[h] = h+2;
            }
        }

        if (indices[h+3] >= stack.length) {
            long tmp = stack[h] + stack[h+1] + stack[h+2];
            if (tmp > sum[h]) {
                sum[h] = stack[h] + stack[h+1] + stack[h+2];
                indices[h] = h+3;
            }
        } else {
            long tmp = stack[h] + stack[h+1] + stack[h+2] + sum[indices[h+3]];
            if (tmp > sum[h]) {
                sum[h] = tmp;
                indices[h] = h+3;
            }
        }
    }
}
