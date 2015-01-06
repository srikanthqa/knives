package com.github.knives.java.iterables;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import com.google.common.collect.Iterables;

public class IterablesTest {

	@Test
	public void testCycle() {
		Iterable<String> roundRobin = Iterables.cycle("A", "B", "C");
		
		
		Iterator<String> first = roundRobin.iterator();
		assertEquals("A", first.next());
		assertEquals("B", first.next());
		
		
		Iterator<String> second = roundRobin.iterator();
		assertNotEquals("C", second.next());
		
		assertEquals("C", first.next());
		assertEquals("A", first.next());
	}

}
