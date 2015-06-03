package com.github.knives.java.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Example of how to implement delay on completable future
 * using ScheduledExecutorService
 */
public class CompletableScheduledFuture {
	
	public static <T> CompletableFuture<Void> thenRunAsync(
			CompletableFuture<? extends T> completableFuture,
			Runnable command, 
			ScheduledExecutorService executor, 
			long delay, TimeUnit unit) {
		return completableFuture.thenRunAsync(command, new ExecutorWrapper(executor, delay, unit));
	}
	
	public static <T, U> CompletableFuture<U> handleAsync(
			CompletableFuture<? extends T> completableFuture,
			BiFunction<? super T, Throwable, ? extends U> fn,
			ScheduledExecutorService executor, 
			long delay, TimeUnit unit) {
		return completableFuture.handleAsync(fn, new ExecutorWrapper(executor, delay, unit));
	}
	
	
	static class ExecutorWrapper implements Executor {

		private final ScheduledExecutorService executor;
		private final long delay;
		private final TimeUnit unit;
		
		public ExecutorWrapper(ScheduledExecutorService executor, long delay, TimeUnit unit) {
			this.executor = executor;
			this.delay = delay;
			this.unit = unit;
		}
		
		@Override
		public void execute(Runnable command) {
			executor.schedule(command, delay, unit);
		}
	}
}
