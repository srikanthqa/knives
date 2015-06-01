package com.github.knives.java.collect;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeMap;

public class TreeRangeMapTest {

	@Test
	public void testAllRange() {
		TreeRangeMap<Integer, String> rangeMap = TreeRangeMap.create();
		rangeMap.put(Range.all(), "val1");
		rangeMap.put(Range.closed(3, 5), "val2");
		
		assertEquals("val2", rangeMap.get(4));
		assertEquals("val1", rangeMap.get(6));
	}

}
