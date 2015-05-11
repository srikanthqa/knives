package com.github.knives.dojo.algorithm;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.github.knives.dojo.algorithm.PrimMinimumSpanningTree.Edge;
import com.github.knives.dojo.datastructure.graph.GridWeightedGraph;

public class PrimMinimumSpanningTreeTest {

	@Test
	public void test4Nodes() {
		GridWeightedGraph graph = new GridWeightedGraph(4);
		graph.setWeight(0, 1, 1);
		graph.setWeight(1, 2, 2);
		graph.setWeight(2, 3, 3);
		graph.setWeight(1, 3, 4);
		
		Edge[] edges = PrimMinimumSpanningTree.compute(graph);
		System.out.println(Arrays.toString(edges));
	}
}
