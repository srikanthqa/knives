package com.github.knives.java.concurrent;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import org.junit.Test;

public class ForkJoinPoolTest {

	public static class SumTask extends RecursiveTask<Long> {
		final private static int THRESHOLD = 10000;
		final private long[] numbers;
		final private int start;
		final private int end;
		
		public SumTask(long[] numbers) {
			this(numbers, 0, numbers.length);
		}
		
		public SumTask(long[] numbers, int start, int end) {
			this.numbers = numbers;
			this.start = start;
			this.end = end;
		}

		@Override
		protected Long compute() {
			final int length = end - start;
			if (length <= THRESHOLD) return computeSequentially();
			
			SumTask leftTask = new SumTask(numbers, start, start + length / 2);
			leftTask.fork();
			
			SumTask rightTask = new SumTask(numbers, start + length / 2, end);
			return rightTask.compute() + leftTask.join();
		}
		
		private long computeSequentially() {
			return LongStream.range(start, end).map(i -> numbers[(int) i]).sum();
		}
	}
	
	@Test
	public void testSumTask() {
		long[] numbers = LongStream.rangeClosed(0, 100000).toArray();
		ForkJoinPool pool = new ForkJoinPool();
		assertEquals(Long.valueOf(100000L * 100001L / 2L), pool.invoke(new SumTask(numbers)));
	}

}
