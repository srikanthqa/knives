package com.github.knives.dojo.problem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JimAndTheOrdersTest {
	@Test
	public void testOrder() {
		Integer[] fan = JimAndTheOrders.order(3, new int[] {1, 2, 3}, new int[] {3, 3, 3});
		assertEquals(1, fan[0].intValue());
		assertEquals(2, fan[1].intValue());
		assertEquals(3, fan[2].intValue());
	}
	
	@Test
	public void testOrder2() {
		Integer[] fan = JimAndTheOrders.order(5, new int[] {8, 4, 5, 3, 4}, 
				new int[] {1, 2, 6, 1, 3});
		// 4 2 5 1 3
		assertEquals(4, fan[0].intValue());
		assertEquals(2, fan[1].intValue());
		assertEquals(5, fan[2].intValue());
		assertEquals(1, fan[3].intValue());
		assertEquals(3, fan[4].intValue());
	}
}
