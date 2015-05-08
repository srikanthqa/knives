package com.github.knives.dojo.problem;

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
public class MinimizeCostOfPairing {

    private final IntPair[] coordinates;
    private final double[][] distance;

    public MinimizeCostOfPairing(IntPair[] coordinates) {
        this.coordinates = coordinates;
        this.distance = new double[coordinates.length][coordinates.length];
    }

    public double solve() {
        return 0.0d;
    }

}
