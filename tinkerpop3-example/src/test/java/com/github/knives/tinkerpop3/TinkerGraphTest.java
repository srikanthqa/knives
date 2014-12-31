package com.github.knives.tinkerpop3;

import org.junit.Test;

import com.tinkerpop.gremlin.process.T;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class TinkerGraphTest {

	@Test
	public void test() {
		Graph g = TinkerGraph.open();
		Vertex marko = g.addVertex(T.id, 1, "name", "marko", "age", 29);
		Vertex vadas = g.addVertex(T.id, 2, "name", "vadas", "age", 27);
		Vertex lop = g.addVertex(T.id, 3, "name", "lop", "lang", "java");
		Vertex josh = g.addVertex(T.id, 4, "name", "josh", "age", 32);
		Vertex ripple = g.addVertex(T.id, 5, "name", "ripple", "lang", "java");
		Vertex peter = g.addVertex(T.id, 6, "name", "peter", "age", 35);
		marko.addEdge("knows", vadas, T.id, 7, "weight", 0.5f);
		marko.addEdge("knows", josh, T.id, 8, "weight", 1.0f);
		marko.addEdge("created", lop, T.id, 9, "weight", 0.4f);
		josh.addEdge("created", ripple, T.id, 10, "weight", 1.0f);
		josh.addEdge("created", lop, T.id, 11, "weight", 0.4f);
		peter.addEdge("created", lop, T.id, 12, "weight", 0.2f);
	}

}
