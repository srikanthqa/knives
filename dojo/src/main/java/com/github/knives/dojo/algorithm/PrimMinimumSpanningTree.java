package com.github.knives.dojo.algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.github.knives.dojo.datastructure.graph.GridWeightedGraph;

public interface PrimMinimumSpanningTree {
	static class Edge {
		private final int dist;
		private final int toNode;
		private final int fromNode;
		
		public Edge(int fromNode, int toNode, int dist) {
			this.fromNode = fromNode;
			this.toNode = toNode;
			this.dist = dist;
		}
		
		public int getDist() {
			return dist;
		}

		public int getToNode() {
			return toNode;
		}

		public int getFromNode() {
			return fromNode;
		}
		
		@Override
		public String toString() {
			return "Edge [dist=" + dist + ", toNode=" + toNode + ", fromNode="
					+ fromNode + "]";
		}
	}
	
	
	/**
	 * DijkstraShortestPath in form of BFS with priority queue
	 */
	static Edge[] compute(GridWeightedGraph graph) {
		final int N = graph.getNumNode();
		final boolean[] visited = new boolean[graph.getNumNode()];
		// ignore 0 edge
		final Edge[] edges = new Edge[N];

		final PriorityQueue<Edge> queue = 
				new PriorityQueue<Edge>(Comparator.comparingInt(it -> it.getDist()));
		
		// a hack, a self loop node 0 to 0 with dist 0
		queue.add(new Edge(0, 0, 0));
		int edgeIndex = 0;
		
		while (!queue.isEmpty()) {
			// poll() instead of pop()
			final Edge currentEdge = queue.poll();
			System.out.println("currentEdge " + currentEdge);
			
			if (visited[currentEdge.getToNode()]) continue;
			
			visited[currentEdge.getToNode()] = true;
			edges[edgeIndex++] = currentEdge;
			
			for (int i = 0; i < N; i++) {
				if (graph.isNeighbor(currentEdge.getToNode(), i) && !visited[i]) {
					queue.add(new Edge(currentEdge.getToNode(), 
							i, graph.getWeight(currentEdge.getToNode(), i)));
				}
			}
		}
		
		return edges;
	}
}
