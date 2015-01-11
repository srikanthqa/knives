package com.github.knives.java.completablefuture;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

/**
 * all method end with "async" has two version that you can supply executor or not
 * If not supply executor, use ForkJoinPool.commonPool()
 */
public class CompletableFutureTest {

	@Test
	public void testSupplyAsync() {
		CompletableFuture<String> fetch = CompletableFuture.supplyAsync(() -> "");
	}
	
	@Test
	public void testCompletedValue() {
		CompletableFuture<String> fetch = CompletableFuture.completedFuture("");
	}

}
