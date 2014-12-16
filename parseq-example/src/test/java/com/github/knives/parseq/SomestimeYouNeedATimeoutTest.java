package com.github.knives.parseq;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.linkedin.parseq.BaseTask;
import com.linkedin.parseq.Context;
import com.linkedin.parseq.Engine;
import com.linkedin.parseq.EngineBuilder;
import com.linkedin.parseq.Task;
import com.linkedin.parseq.Tasks;
import com.linkedin.parseq.promise.Promise;
import com.linkedin.parseq.promise.Promises;
import com.linkedin.parseq.promise.SettablePromise;

public class SomestimeYouNeedATimeoutTest {

	@Test
	public void testTimeout() throws Exception {
		final int numCores = Runtime.getRuntime().availableProcessors();
		final ExecutorService taskScheduler = Executors.newFixedThreadPool(numCores + 1);
		final ScheduledExecutorService timerScheduler = Executors.newSingleThreadScheduledExecutor(); 

		final Engine engine = new EngineBuilder()
		        .setTaskExecutor(taskScheduler)
		        .setTimerScheduler(timerScheduler)
		        .build();
		
		Task<String> sleepingTask = new BaseTask<String>("sleepingBeauty") {
			@Override
			protected Promise<String> run(Context context) throws Throwable {
				final SettablePromise<String> promise = Promises.settable();
				// some empty promise the sleeping beauty makes
				return promise;
			}
		};
		
		Task<String> wakeup = Tasks.timeoutWithError("wakeup", 1, TimeUnit.SECONDS, sleepingTask);
		
		engine.run(wakeup);
		wakeup.await(3, TimeUnit.SECONDS);
		System.out.println(wakeup.isDone());
		assertTrue(wakeup.isFailed());
		System.out.println(wakeup.getError());
	}
}
