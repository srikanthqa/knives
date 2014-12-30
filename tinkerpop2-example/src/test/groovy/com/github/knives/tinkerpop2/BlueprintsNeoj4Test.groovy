package com.github.knives.tinkerpop2

import org.junit.Test

import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph

class BlueprintsNeoj4Test {

	@Test
	void testNeo4j() {
		Graph graph = new Neo4jGraph("/tmp/my_graph");
		Vertex a = graph.addVertex(null);
		Vertex b = graph.addVertex(null);
		a.setProperty("name","marko");
		b.setProperty("name","peter");
		Edge e = graph.addEdge(null, a, b, "knows");
		e.setProperty("since", 2006);
		graph.shutdown();
	}
}
