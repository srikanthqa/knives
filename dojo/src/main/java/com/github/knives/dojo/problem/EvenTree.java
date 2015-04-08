package com.github.knives.dojo.problem;

import com.github.knives.dojo.datastructure.Edge;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public interface EvenTree {

    public static class Node {
        final public int n;
        final public List<Node> neighbors = new LinkedList<Node>();

        public Node(int n) {
            this.n = n;
        }
    }

    public static int compute(final Edge[] edges, final Node[] graph) {
        int removeEdge = 0;
        for (Edge edge : edges) {
            Node p = graph[edge.p];
            Node q = graph[edge.q];
            if (sizeSubtree(graph.length, p, q) % 2 == 0 && sizeSubtree(graph.length, q, p) % 2 == 0) {
                p.neighbors.remove(q);
                q.neighbors.remove(p);
                removeEdge++;
            }
        }

        return removeEdge;
    }

    public static int sizeSubtree(int n, final Node from, final Node ignore) {
        final boolean[] visited = new boolean[n];
        visited[ignore.n] = true;
        visited[from.n] = true;

        final Stack<Node> nodes = new Stack<>();
        nodes.push(from);

        int size = 0;
        while (!nodes.isEmpty()) {
            Node top = nodes.pop();
            size++;

            for (Node node : top.neighbors) {
                if (!visited[node.n]) {
                    nodes.push(node);
                    visited[node.n] = true;
                }
            }
        }

        return size;
    }
}
