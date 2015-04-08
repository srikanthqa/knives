package com.github.knives.dojo.datastructure.princeton;

import org.junit.Test;

public class FordFulkersonTest {
    @Test
    public void test() {
        // create flow network with V vertices and E edges
        int V = 5;
        int E = 15;
        int s = 0, t = V-1;
        FlowNetwork G = new FlowNetwork(V, E);
        System.out.println(G);

        // compute maximum flow and minimum cut
        FordFulkerson maxflow = new FordFulkerson(G, s, t);
        System.out.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && e.flow() > 0)
                    System.out.println("   " + e);
            }
        }

        // print min-cut
        System.out.print("Min cut: ");
        for (int v = 0; v < G.V(); v++) {
            if (maxflow.inCut(v)) System.out.print(v + " ");
        }
        System.out.println();

        System.out.println("Max flow value = " +  maxflow.value());
    }
}
