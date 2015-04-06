package com.github.knives.dojo.datastructure;

import java.util.stream.IntStream;

/**
 * Disjoint-set data structure (merge-find set)
 * keep tracks of a set of elements partitioned into a number of disjoint (nonoverlapping) subsets
 *
 * pair p q as meaning p is connected to q. We assume that "is connected to" is an equivalence relation:
 *
 * 1. symmetric: If p is connected to q, then q is connected to p.
 * 2. transitive: If p is connected to q and q is connected to r, then p is connected to r.
 * 3. reflexive: p is connected to p.
 *
 * Operations:
 *
 * find() - returns an item from the set that server as its representative. By comparing
 * the result of find() to determine whether two elements are in the same subset
 *
 * union() join two subsets into a single subset
 *
 * This native implementation provides no better than linked list approach
 */
public class NativeUnionFind {
    final private int[] parent;

    public NativeUnionFind(int n) {
        parent = new int[n];
        // initially every element is in disjoint set
        IntStream.range(0, n).forEach(it -> parent[it] = it);
    }

    public int find(int i) {
        while (parent[i] != i) {
            i = parent[i];
        }

        return i;
    }

    public void union(int i, int j) {
        int iRoot = find(i);
        int jRoot = find(j);
        parent[iRoot] = jRoot;
    }
}
