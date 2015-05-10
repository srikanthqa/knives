package com.github.knives.dojo.datastructure.graph;

public class GridWeightedGraph {
	private final int[][] weight;
	
	public GridWeightedGraph(int nNode) {
		weight = new int[nNode][nNode];
	}
	
	public void setWeight(int i, int j, int v) {
		weight[Math.max(i, j)][Math.min(i, j)] = v;
	}
	
	public int getWeight(int i, int j) {
		return weight[Math.max(i, j)][Math.min(i, j)];
	}
}
