package com.github.knives.java.listenablefuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ListenableFutureTest {
	final private static Logger LOG = LoggerFactory.getLogger(ListenableFutureTest.class);
	
	/**
	 * Convert Future to ListenableFuture by JdkFutureAdapters
	 */
	@Test
	public void testTheFuture() throws Exception {
		ExecutorService executorService = Executors.newSingleThreadExecutor(
				new ThreadFactoryBuilder()
					.setNameFormat("test-pool-%d")
					.build());
		
		Future<Integer> result = executorService.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(2000);
				return new Integer(1);
			}
		});
		
		final ListenableFuture<Integer> listenableFuture = JdkFutureAdapters.listenInPoolThread(result, executorService);
		
		LOG.info("Receive result=[{}]", listenableFuture.get());
	}
	
	/**
	 * ListeningExecutorService wrapper
	 */
	@Test
	public void testTheListenableFuture() throws Exception {
		ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(
			Executors.newSingleThreadExecutor(
				new ThreadFactoryBuilder()
					.setNameFormat("test-pool-%d")
					.build()));
		
		
		ListenableFuture<Integer> result = listeningExecutorService.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(3000);
				return new Integer(5);
			}
		});
		
		Futures.addCallback(result, new FutureCallback<Integer>() {
			@Override
			public void onSuccess(Integer result) {
				LOG.info("Receive result=[{}]", result);
			}

			@Override
			public void onFailure(Throwable t) {
				LOG.error("Receive exception", t);
				
			}
		}, listeningExecutorService);
		
		result.get();
	}

	@Test
	public void testTransform() throws Exception {
		ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(
				Executors.newSingleThreadExecutor(
					new ThreadFactoryBuilder()
						.setNameFormat("test-pool-%d")
						.build()));
			
			
		ListenableFuture<Integer> result = listeningExecutorService.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(3000);
				return new Integer(5);
			}
		});
		
		Function<Integer, Integer> add5 = adder(5);
		
		ListenableFuture<Integer> result2 = Futures.transform(result, add5, listeningExecutorService);
		
		LOG.info("Receive result=[{}]", result2.get());
	}
	
	private Function<Integer, Integer> adder(final Integer val) {
		return new Function<Integer, Integer>() {
			@Override
			public Integer apply(Integer input) {
				return input + val;
			}
		};
	}
	
}
