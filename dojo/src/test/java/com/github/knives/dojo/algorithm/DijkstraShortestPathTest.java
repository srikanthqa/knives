package com.github.knives.dojo.algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.knives.dojo.datastructure.graph.GridGraph;
import com.github.knives.dojo.datastructure.graph.GridWeightedGraph;

public class DijkstraShortestPathTest {

	@Test
	public void test2Nodes() {
		GridWeightedGraph graph = new GridWeightedGraph(2);
		graph.setWeight(0, 1, 5);
		assertEquals(5, DijkstraShortestPath.distance(graph, 0, 1));
		assertEquals(5, DijkstraShortestPath.distance(graph, 1, 0));
	}
	
	@Test
	public void test3Nodes() {
		GridWeightedGraph graph = new GridWeightedGraph(3);
		graph.setWeight(0, 1, 5);
		graph.setWeight(1, 2, 5);

		
		assertEquals(10, DijkstraShortestPath.distance(graph, 0, 2));
		assertEquals(10, DijkstraShortestPath.distance(graph, 2, 0));
	}
	
	@Test
	public void test4Nodes() {
		GridWeightedGraph graph = new GridWeightedGraph(4);
		graph.setWeight(0, 1, 1);
		graph.setWeight(1, 2, 2);
		graph.setWeight(2, 3, 3);
		graph.setWeight(1, 3, 4);
		
		assertEquals(5, DijkstraShortestPath.distance(graph, 0, 3));
		assertEquals(5, DijkstraShortestPath.distance(graph, 3, 0));
	}
	
	@Test
	public void testDisconnect() {
		GridWeightedGraph graph = new GridWeightedGraph(3);
		graph.setWeight(0, 1, 5);

		
		assertEquals(-1, DijkstraShortestPath.distance(graph, 0, 2));
		assertEquals(-1, DijkstraShortestPath.distance(graph, 2, 0));
	}

}
