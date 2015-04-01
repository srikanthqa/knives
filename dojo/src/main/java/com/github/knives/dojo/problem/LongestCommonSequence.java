package com.github.knives.dojo.problem;

import java.util.Arrays;

public interface LongestCommonSequence {
    public static char NONE = 'N';
    public static char DIAGONAL = '\\';
    public static char VERTICAL = '|';
    public static char HORIZONTAL = '-';

    public static int[] compute(int[] a, int[] b) {
        final char[][] trace = new char[a.length+1][b.length+1];
        final int[][] len = new int[a.length+1][b.length+1];

        for (int i = 0; i <= a.length; i++)  {
            for (int j = 0; j <= b.length; j++) {
                if (i == 0 || j == 0) {
                    len[i][j] = 0;
                    trace[i][j] = NONE;
                } else if (a[i-1] == b[j-1]) {
                    //System.out.println("match " + (i-1) + " " + (j-1));
                    len[i][j] = len[i - 1][j - 1] + 1;
                    trace[i][j] = DIAGONAL;
                } else {
                    if (len[i - 1][j] >= len[i][j - 1]) {
                        len[i][j] = len[i - 1][j];
                        trace[i][j] = VERTICAL;
                    } else {
                        len[i][j] = len[i][j - 1];
                        trace[i][j] = HORIZONTAL;
                    }
                }
            }
        }

        final int[] result = new int[len[a.length][b.length]];
        int i = a.length;
        int j = b.length;
        int k = result.length - 1;

        //System.out.println(result.length);

        while (i > 0 && j > 0) {
            if (trace[i][j] == DIAGONAL) {
                result[k] = a[i-1];
                k = k - 1;
                i = i - 1;
                j = j - 1;
            } else {
                if (trace[i][j] == HORIZONTAL) {
                    j = j - 1;
                } else {
                    i = i - 1;
                }
            }
        }

        /*
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.out.println();

        for (int x = 0; x < a.length; x++) {
            System.out.println(Arrays.toString(trace[x]));
        }

        System.out.println();

        for (int x = 0; x < a.length; x++) {
            System.out.println(Arrays.toString(len[x]));
        }

        System.out.println();
        System.out.println(Arrays.toString(result));
        */

        return result;
    }
}
