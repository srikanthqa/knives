package com.github.knives.dojo.problem;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Maintain top 10 (or X) highest number during a stream of number
 */
public class OnlineStreamRank {
	
	private final int maxSize = 10;
	private final PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(maxSize + 1, 
			Comparator.naturalOrder());
	
	public void add(int i) {
		minHeap.add(i);
		
		// remove smallest element
		if (minHeap.size() > maxSize)
			minHeap.poll();
	}
	
	public Integer[] get() {
		return minHeap.toArray(new Integer[maxSize]);
	}
}
