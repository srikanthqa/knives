package com.github.knives.java.completablefuture;

import static org.junit.Assert.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;

import org.junit.Test;

public class CompletableScheduledFutureTest {

	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	@Test
	public void testThenRunAsync() throws InterruptedException, ExecutionException, TimeoutException {
		CompletableFuture<Void> sleepingFuture = CompletableFuture
			.runAsync(() -> { 
				System.out.println("going to sleep"); 
				try {
					Thread.sleep(5000L);
				} catch (Exception ignore) {} 
				System.out.println("done sleeping"); 
			}, executor);
		
		CompletableFuture<Void> snoozingFuture = CompletableScheduledFuture.thenRunAsync(sleepingFuture, 
				() -> System.out.println("done snoozing"), 
				executor, 5L, TimeUnit.SECONDS);
		
		snoozingFuture.get(11, TimeUnit.SECONDS);
	}
	
	
	@Test
	public void testHandleAsync() throws InterruptedException, ExecutionException, TimeoutException {
		CompletableFuture<Void> sleepingFuture = CompletableFuture
			.runAsync(() -> { 
				System.out.println("going to sleep"); 
				try {
					Thread.sleep(5000L);
				} catch (Exception ignore) {} 
				System.out.println("done sleeping"); 
			}, executor);
		
		CompletableFuture<Void> snoozingFuture = CompletableScheduledFuture.handleAsync(sleepingFuture, 
				(Void t, Throwable u) -> { System.out.println("done snoozing"); return null; }, 
				executor, 5L, TimeUnit.SECONDS);
		
		snoozingFuture.get(11, TimeUnit.SECONDS);
	}
}
