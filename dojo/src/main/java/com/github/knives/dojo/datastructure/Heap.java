package com.github.knives.dojo.datastructure;

import com.github.knives.dojo.utils.ListUtils;

import java.util.*;

/**
 * Re-implementation of java PriorityQueue
 *
 * Heap overview:
 *
 * 1. Maintain order of parent's value always greater / less than child value (max-heap vs min-heap)
 * 2. Parent index = (child index - 1) / 2 (integer division)
 * 3. left child index = 2 * parent index + 1
 * 4. right child index = 2 * parent index + 2
 * 5. find max/min is O(1)
 * 6. delete max/min or insert is O(log n) = heapify / sift up / sift down process
 * 7. random access delete could be implemented but it takes O(log n)
 */
public class Heap<T> {
    private final List<T> heap;
    private final Comparator<T> comparator;

    public Heap(Comparator<T> comparator) {
        this.comparator = comparator;
        this.heap = new ArrayList<T>();
    }

    public Heap(List<T> list, Comparator<T> comparator) {
        if (!(list instanceof RandomAccess)) {
            throw new IllegalArgumentException(
                    "The list passed has to support random access.");
        }

        this.comparator = comparator;
        this.heap = list;
        heapify();
    }

    public Heap(T[] array, Comparator<T> comparator) {
        this(new ArrayList<T>(Arrays.asList(array)), comparator);
    }

    public int size() {
        return heap.size();
    }

    public T peek() throws EmptyHeapException {
        if (heap.isEmpty()) {
            throw new EmptyHeapException();
        }

        return heap.get(0);
    }

    public T pop() throws EmptyHeapException {
        if (heap.isEmpty()) {
            throw new EmptyHeapException();
        }

        ListUtils.swap(heap, 0, heap.size() - 1);
        T toReturn = heap.remove(heap.size() - 1);
        heapify(0);

        return toReturn;
    }

    public void add(T element) {
        heap.add(element);
        heapify();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        heap.clear();
    }

    private void heapify() {
        for (int rootIndex = parentIndex(heap.size() - 1); rootIndex >= 0; rootIndex--) {
            heapify(rootIndex);
        }
    }

    private void heapify(int rootIndex) {
        heapify(heap.size(), rootIndex);
    }

    private void heapify(int length, int rootIndex) {
        int indexToBeSwapped = rootIndex;

        while (true) {
            if (leftChildIndex(rootIndex) < length) {
                if (comparator.compare(heap.get(leftChildIndex(rootIndex)),
                        heap.get(indexToBeSwapped)) > 0) {
                    indexToBeSwapped = leftChildIndex(rootIndex);
                }
            }

            if (rightChildIndex(rootIndex) < length) {
                if (comparator.compare(heap.get(rightChildIndex(rootIndex)),
                        heap.get(indexToBeSwapped)) > 0) {
                    indexToBeSwapped = rightChildIndex(rootIndex);
                }
            }

            if (indexToBeSwapped == rootIndex) {
                break;
            }

            ListUtils.swap(heap, rootIndex, indexToBeSwapped);
            rootIndex = indexToBeSwapped;
        }
    }

    private static int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    private static int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    private static int parentIndex(int index) {
        return (index - 1) / 2;
    }

    public static class EmptyHeapException extends Exception { }
}
