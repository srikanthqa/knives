package com.github.knives.dojo.datastructure.graph;

/**
 * Represent graph by grid of boolean
 * 
 * Each node represent by i, and j integer
 * Unweighted graph
 * Undirect graph
 */
public class GridGraph {
	private final boolean[][] connected;
	private final int nNode;

	public GridGraph(int nNode) {
		this.nNode = nNode;
		connected = new boolean[nNode][nNode];
	}
	
	public boolean isNeighbor(int i, int j) {
		// set larger index is the first index
		return connected[Math.max(i, j)][Math.min(i, j)];
	}
	
	public void connect(int i, int j) {
		connected[Math.max(i, j)][Math.min(i, j)] = true;
	}
	
	public int getNumNode() {
		return nNode;
	}
}
