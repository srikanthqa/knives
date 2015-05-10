package com.github.knives.dojo.algorithm;

import com.github.knives.dojo.datastructure.IntPair;

public interface GridNavigation {
	// left, right, top, and bottom
	static IntPair[] offset = new IntPair[] {
		new IntPair(-1, 0),
		new IntPair(1, 0),
		new IntPair(0, 1),
		new IntPair(0, -1),
	};
	
	static IntPair[] getNeighbor(IntPair coordinate) {
		final IntPair[] neighbors = new IntPair[offset.length];
		
		for (int i = 0; i < offset.length; i++) {
			neighbors[i] = new IntPair(coordinate.getFirst() + offset[i].getFirst(),
					coordinate.getSecond() + offset[i].getSecond());
		}
		
		return neighbors;
	}
}
