package com.github.knives.tinkerpop2

import org.junit.Test

import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.tg.TinkerGraph

class BlueprintsTinkerGraph {
	@Test
	void testTinkerGraph() {
		Graph graph = new TinkerGraph("/tmp/tinkergraph", TinkerGraph.FileType.GML);
		Vertex a = graph.addVertex(null);
		Vertex b = graph.addVertex(null);
		a.setProperty("name","marko");
		b.setProperty("name","peter");
		Edge e = graph.addEdge(null, a, b, "knows");
		e.setProperty("since", 2006);
		graph.shutdown();
	}
}
