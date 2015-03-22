package com.github.knives.dojo.datastructure;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class HeapTest {
    @Test
    public void testHeap() throws Heap.EmptyHeapException {
        final Heap<Integer> heap = new Heap<Integer>(Comparator.<Integer>naturalOrder());

        heap.add(1);
        heap.add(2);
        heap.add(5);

        assertEquals(Integer.valueOf(5), heap.pop());

        heap.clear();

        heap.add(5);
        heap.add(2);
        heap.add(1);

        assertEquals(Integer.valueOf(5), heap.pop());
    }
}
