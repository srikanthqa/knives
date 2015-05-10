package com.github.knives.dojo.algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.github.knives.dojo.datastructure.graph.GridWeightedGraph;

public interface DijkstraShortestPath {
	static class Node {
		private final int distTo;
		private int node;
		
		public Node(int node, int distTo) {
			this.node = node;
			this.distTo = distTo;
		}
		
		public int getDistTo() {
			return distTo;
		}
		
		public int getNode() {
			return node;
		}
		
		@Override
		public String toString() {
			return "Node [distTo=" + distTo + ", node=" + node + "]";
		}
	}
	
	
	/**
	 * DijkstraShortestPath in form of BFS with priority queue
	 */
	static int distance(GridWeightedGraph graph, int src, int dest) {
		boolean[] visited = new boolean[graph.getNumNode()];
		PriorityQueue<Node> queue = new PriorityQueue<Node>(Comparator.comparingInt(it -> it.getDistTo()));
		// add() instead of push()
		queue.add(new Node(src, 0));
		
		while (!queue.isEmpty()) {
			// poll() instead of pop()
			final Node currentNode = queue.poll();
			System.out.println("currentNode " + currentNode);
			
			if (currentNode.getNode() == dest) return currentNode.distTo;
			
			visited[currentNode.getNode()] = true;
			
			for (int i = 0; i < graph.getNumNode(); i++) {
				if (graph.isNeighbor(currentNode.getNode(), i) && !visited[i]) {
					queue.add(new Node(i, 
							currentNode.getDistTo() + graph.getWeight(currentNode.getNode(), i)));
				}
			}
		}
		
		return -1;
	}
}
