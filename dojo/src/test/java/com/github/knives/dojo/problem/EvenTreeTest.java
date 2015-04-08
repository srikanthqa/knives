package com.github.knives.dojo.problem;

import com.github.knives.dojo.datastructure.Edge;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EvenTreeTest {
    @Test
    public void test() {

        final EvenTree.Node[] graph = new EvenTree.Node[10];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new EvenTree.Node(i);
        }

        final Edge[] edges = new Edge[9];
        insertEdge(graph, edges, 0, 2, 1);
        insertEdge(graph, edges, 1, 3, 1);
        insertEdge(graph, edges, 2, 4, 3);
        insertEdge(graph, edges, 3, 5, 2);
        insertEdge(graph, edges, 4, 6, 1);
        insertEdge(graph, edges, 5, 7, 2);
        insertEdge(graph, edges, 6, 8, 6);
        insertEdge(graph, edges, 7, 9, 8);
        insertEdge(graph, edges, 8, 10, 8);

        assertEquals(2, EvenTree.compute(edges, graph));
    }

    public static void insertEdge(EvenTree.Node[] graph, Edge[] edges, int i, int q, int p) {
        graph[q-1].neighbors.add(graph[p-1]);
        graph[p-1].neighbors.add(graph[q-1]);
        edges[i] = new Edge(q-1, p-1);
    }
}
