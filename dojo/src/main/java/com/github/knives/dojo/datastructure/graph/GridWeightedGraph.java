package com.github.knives.dojo.datastructure.graph;

public class GridWeightedGraph {
	private final int[][] weight;
	private final int nNode;
	
	public GridWeightedGraph(int nNode) {
		weight = new int[nNode][nNode];
		this.nNode = nNode;
	}
	
	public void setWeight(int i, int j, int v) {
		weight[Math.max(i, j)][Math.min(i, j)] = v;
	}
	
	public int getWeight(int i, int j) {
		return weight[Math.max(i, j)][Math.min(i, j)];
	}
	
	public boolean isNeighbor(int i, int j) {
		return weight[Math.max(i, j)][Math.min(i, j)] != 0;
	}
	
	public int getNumNode() {
		return nNode;
	}
}
