package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * Given a squared sized grid G of size N in which each cell has a lowercase letter.
 * Denote the character in the ith row and in the jth column as G[i][j].
 *
 * You can perform one operation as many times as you like:
 * Swap two column adjacent characters in the same row G[i][j] and G[i][j+1] for all valid i,j.
 *
 * Is it possible to rearrange the grid such that the following condition is true?
 *
 * G[i][1]≤G[i][2]≤⋯≤G[i][N] for 1≤i≤N and
 * G[1][j]≤G[2][j]≤⋯≤G[N][j] for 1≤j≤N
 * In other words, is it possible to rearrange the grid such that every row and every column is lexicographically sorted?
 */
public interface GridChallenge {
    /**
     * If you can move column in same row around, then it should be sorted to fulfill the first condition.
     *
     * When each row is sorted in ascending order, check the order of each column.
     * Because if this configuration of grid is not fulfilled the condition, no possible configuration can.
     */
    public static boolean canBeSort(final char[][] grid, final int n) {
        for (int i = 0; i < n; i++) {
            Arrays.sort(grid[i]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[j-1][i] > grid[j][i]) {
                    return false;
                }
            }
        }

        /*
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(grid[i]));
        }
        */

        return true;
    }
}
