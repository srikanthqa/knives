package com.github.knives.dojo.problem;

import java.util.Arrays;

import com.github.knives.dojo.datastructure.IntPair;

/**
 * Let (x,y) be the coordinates of a student's house on a 2D plane. There are 2N students and we wanted to pair them
 * into N groups. Let d_i be the distance between the house of 2 students in group i. Form N groups such that
 * cost = (sum from i to N) d_i is minimized. Output the minimized cost
 *
 * Constraint
 * 1 <= N <= 8
 * 0 <= x,y <= 1000
 *
 */
public interface MinimizeCostOfPairing {

	// Example of dynamic program with bitset / hash index
    static double compute(IntPair[] coordinates) {
        final int N = coordinates.length;

        final double[][] distance = new double[N][N];
        
        computeDistance(coordinates, distance);
        
        final double[] minDistance = new double[1 << N];
        Arrays.fill(minDistance, -1.0d);
        
        return computeDistance(0, (1<<N)-1, N, distance, minDistance);
    }

    static void computeDistance(IntPair[] coordinates, double[][] distance) {
    	for (int i = 0; i < coordinates.length; i++) {
    		for (int j = i+1; j < coordinates.length; j++) {
    			distance[i][j] 
					= distance[j][i] 
					= Math.hypot(coordinates[i].getFirst() - coordinates[j].getFirst() , 
								 coordinates[i].getSecond() - coordinates[j].getSecond());
    		}
    	}
    }
    
    static double computeDistance(int currentSet, int targetSet, int N, double[][] distance, double[] minDistance) {
    	if (minDistance[currentSet] > -0.5) {
    		return minDistance[currentSet];
    	}
    	
    	if (currentSet == targetSet) {
    		return minDistance[targetSet] = 0.0d;
    	}
    	
    	double answer = Double.MAX_VALUE;
    	
    	int p1, p2;
    	// find the first p1
    	for (p1 = 0; p1 < N; p1++) {
    		int mask = 1 << p1;
    		if ((currentSet & mask) == 0) {
    			break;
    		}
    	}
    	
    	for (p2 = p1+1; p2 < N; p2++) {
    		int mask = 1 << p2;
    		if ((currentSet & mask) == 0) {
    			answer = Math.min(answer, distance[p1][p2] + 
    					computeDistance(currentSet | (1<<p1) | (1<<p2), targetSet, N, distance, minDistance));
    		}
    	}
    	
    	return minDistance[currentSet] = answer;
    }
}
