package com.github.knives.dojo.problem;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Alice gives a wooden board composed of M X N wooden square pieces to Bob and asks him to 
 * find the minimal cost of breaking the board into square wooden pieces. Bob can cut the 
 * board along horizontal and vertical lines, and each cut divides the board in smaller parts.
 * Each cut has a cost depending on whether the cut is made along a horizontal or a vertical line.
 *
 * Let us denote the costs of cutting it along consecutive vertical lines by x1, x2, ..., xn-1, 
 * and the cost of cutting it along horizontal lines by y1, y2, ..., ym-1. If a cut (of cost c) 
 * is made and it passes through n segments, then total cost of this cut will be n*c.
 *
 * The cost of cutting the whole board into single squares is the sum of the cost of successive 
 * cuts used to cut the whole board into square wooden pieces of size 1x1. Bob should compute 
 * the minimal cost of breaking the whole wooden board into squares of size 1x1.
 *
 * Bob needs your help to find the minimal cost. Can you help Bob with this challenge?
 * 
 * A single integer in the first line T, stating the number of test cases. T testcases follow. 
 * For each test case, the first line contains two positive integers M and N separated by a single space. 
 * In the next line, there are integers y1, y2, ..., ym-1, separated by spaces. Following them are 
 * integers x1, x2, ..., xn-1, separated by spaces.
 */
public interface BoardCutting {
	// row = y cost
	// col = x cost
	// return the cost
	static long cut(long[] row, long[] col) {
		Arrays.sort(row);
		Arrays.sort(col);
		
		//System.out.println(Arrays.toString(row));
		//System.out.println(Arrays.toString(col));
		
		long cost = 0;
		long numRowSection = 1;
		long numColSection = 1;
		int y = row.length - 1;
		int x = col.length - 1;
		
		while (y >= 0 && x >= 0) {
			// number of segment don't matter
			// high cost still result in higher cost when number of section increases
			// comparison is not between partial cost
			if (row[y] > col[x]) {
				cost += row[y] * numColSection;
				numRowSection++;
				//System.out.println("cut row " + y + " " + row[y] + " " + numColSection + " at total cost " + (row[y] * numColSection));
				y--;
			} else {
				cost += col[x] * numRowSection;
				numColSection++;
				//System.out.println("cut col " + x + " " + col[x] + " " + numRowSection + " at total cost " + (col[x] * numRowSection));
				x--;
			} 
		}
		
		for (; y >= 0; y--) {
			cost += row[y] * numColSection;
			//System.out.println("cut row " + y + " " + row[y] + " at cost " + row[y] * numColSection);
		}
		
		for (; x >= 0; x--) {
			cost += col[x] * numRowSection;
			//System.out.println("cut col " + x + " " + col[x] + " at cost " + (col[x] * numRowSection));
		}
		
		return cost;
	}
	
	
    static void main(Scanner scanner) {
        final long modulo = 1000000007L;
        
        final int T = scanner.nextInt();
        for (int t = 0; t < T; t++) {
            final int M = scanner.nextInt();
            final int N = scanner.nextInt();
            final long[] row = new long[M-1];
            final long[] col = new long[N-1];
            for (int i = 0; i < M-1; i++) {
                row[i] = scanner.nextLong();
            }
            
            for (int i = 0; i < N-1; i++) {
                col[i] = scanner.nextLong();
            }
            
            System.out.println(cut(row, col) % modulo);
        }
    }
}
