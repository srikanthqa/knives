package com.github.knives.dojo.algorithm;

import java.util.LinkedList;
import java.util.Queue;

import com.github.knives.dojo.datastructure.graph.GridGraph;

public interface BreadthFirstSearch {
	static boolean isConnected(GridGraph graph, int src, int dest) {
		boolean[] visited = new boolean[graph.getNumNode()];
		Queue<Integer> queue = new LinkedList<Integer>();
		// add() instead of push()
		queue.add(src);
		
		while (!queue.isEmpty()) {
			// poll() instead of pop()
			final int currentNode = queue.poll();
			System.out.println("currentNode " + currentNode);
			if (currentNode == dest) return true;
			
			visited[currentNode] = true;
			
			for (int i = 0; i < graph.getNumNode(); i++) {
				if (graph.isNeighbor(currentNode, i) && !visited[i]) {
					queue.add(i);
				}
			}
		}
		
		return false;
	}
}
