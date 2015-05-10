package com.github.knives.dojo.algorithm;

import java.util.Stack;

import com.github.knives.dojo.datastructure.graph.GridGraph;

public interface DepthFirstSearch {
	static boolean isConnected(GridGraph graph, int src, int dest) {
		boolean[] visited = new boolean[graph.getNumNode()];
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(src);
		
		while (!stack.isEmpty()) {
			final int currentNode = stack.pop();
			System.out.println("currentNode " + currentNode);
			if (currentNode == dest) return true;
			
			visited[currentNode] = true;
			
			for (int i = 0; i < graph.getNumNode(); i++) {
				if (graph.isNeighbor(currentNode, i) && !visited[i]) {
					stack.push(i);
				}
			}
		}
		
		return false;
	}
}
