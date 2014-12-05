package com.github.knives.instrumentation.test;

import com.github.knives.instrumentation.utils.MemoryUtil;

public class MemoryUtilTest {

    public static void main(String [] args) {
        System.out.println(MemoryUtil.memoryUsageOf(new Square(5,5)));
    }
    
    public static class Square {
    	final private int x,y;

		public Square(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
    	
    }
}
