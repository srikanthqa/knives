package com.github.knives.dojo.algorithm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.knives.dojo.datastructure.graph.GridGraph;

public class BreadthFirstSearchTest {
	@Test
	public void test2Nodes() {
		GridGraph graph = new GridGraph(2);
		graph.connect(1, 0);
		assertTrue(BreadthFirstSearch.isConnected(graph, 0, 1));
		assertTrue(BreadthFirstSearch.isConnected(graph, 1, 0));
	}
	
	@Test
	public void test3Nodes() {
		GridGraph graph = new GridGraph(3);
		graph.connect(1, 0);
		graph.connect(1, 2);
		
		assertTrue(BreadthFirstSearch.isConnected(graph, 2, 0));
		assertTrue(BreadthFirstSearch.isConnected(graph, 0, 2));
	}
	
	@Test
	public void testDisconnect() {
		GridGraph graph = new GridGraph(3);
		graph.connect(1, 0);

		assertFalse(BreadthFirstSearch.isConnected(graph, 2, 0));
		assertFalse(BreadthFirstSearch.isConnected(graph, 0, 2));
	}
}
