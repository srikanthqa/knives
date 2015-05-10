package com.github.knives.dojo.algorithm;

import java.util.LinkedList;
import java.util.Queue;

import com.github.knives.dojo.datastructure.graph.GridGraph;

public interface BreadthFirstSearch {
	static boolean isConnected(GridGraph graph, int src, int dest) {
		boolean[] visited = new boolean[graph.getNumNode()];
		Queue<Integer> stack = new LinkedList<Integer>();
		// add() instead of push()
		stack.add(src);
		
		while (!stack.isEmpty()) {
			// poll() instead of pop()
			final int currentNode = stack.poll();
			System.out.println("currentNode " + currentNode);
			if (currentNode == dest) return true;
			
			visited[currentNode] = true;
			
			for (int i = 0; i < graph.getNumNode(); i++) {
				if (graph.isNeighbor(currentNode, i) && !visited[i]) {
					stack.add(i);
				}
			}
		}
		
		return false;
	}
}
