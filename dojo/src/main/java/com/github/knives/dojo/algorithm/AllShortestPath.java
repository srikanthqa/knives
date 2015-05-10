package com.github.knives.dojo.algorithm;

/**
 * Floyd-Warshall
 * 
 * adj[i][j] = min(adj[i][j], adj[i][k] + adj[k][j]);
 * 
 * Good for non-negative cycle graph
 */
public interface AllShortestPath {
	static long[][] distance(Integer[][] graph, int N) {
		final long[][] adj = new long[N][N];
		// maintain int next[][] node matrix to trace
		// Integer.MAX_VALUE is infinity
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				adj[i][j] = graph[i][j] == null ? Integer.MAX_VALUE : graph[i][j];
			}
	 	}
		
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					adj[i][j] = Math.min(adj[i][j], adj[i][k] + adj[k][j]);
				}
		 	}
		}
		
		return adj;
	}
}
