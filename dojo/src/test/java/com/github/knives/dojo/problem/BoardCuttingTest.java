package com.github.knives.dojo.problem;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Test;

public class BoardCuttingTest {
	@Test
	public void testCut() {
		assertEquals(4, BoardCutting.cut(new long[] {2}, new long[] {1}));
		
		// y5, x1, y3, y1, x3, y2, y4 and x2
		// 4 + 4*2 + 3*2 + 2*2 + 2*4 + 1*3 + 1*3 + 1*6
		assertEquals(42, BoardCutting.cut(new long[] {2, 1, 3, 1, 4}, new long[] {4, 1, 2}));
	}
	
	@Test
	public void testExample() {
		BoardCutting.main(new Scanner(ClassLoader.getSystemResourceAsStream("boardcutting/input_1.txt")));
	}
}
