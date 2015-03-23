package com.github.knives.dojo.utils;

import java.util.List;

/**
 * Common methods required for manipulating lists.
 */
public interface ListUtils {

	// Swaps elements of a list.
	public static <T> void swap(List<T> list, int fooIndex, int barIndex) {
		T tmp = list.get(fooIndex);
		list.set(fooIndex, list.get(barIndex));
		list.set(barIndex, tmp);
	}
}
