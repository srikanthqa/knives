package com.github.knives.java.timelimiter;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.UncheckedTimeoutException;

public class SimpleTimeLimiterTest {

	@Test(expected = UncheckedTimeoutException.class)
	public void testAlwaysLate() throws Exception {
		SimpleTimeLimiter timeLimiter = new SimpleTimeLimiter(Executors.newSingleThreadExecutor(
				new ThreadFactoryBuilder()
				.setNameFormat("test-pool-%d")
				.build()));
		
		timeLimiter.callWithTimeout(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				Thread.sleep(TimeUnit.SECONDS.toMillis(5));
				return Integer.valueOf(0);
			}
			
		}, 2, TimeUnit.SECONDS, true);

	}
	
	@Test
	public void testProxy() throws Exception {
		SimpleTimeLimiter timeLimiter = new SimpleTimeLimiter(Executors.newSingleThreadExecutor(
				new ThreadFactoryBuilder()
				.setNameFormat("test-pool-%d")
				.build()));
		
		try {
			MyService myBetterService = timeLimiter.newProxy(new MyBetterService(), MyService.class, 2, TimeUnit.SECONDS);
			assertEquals(Integer.valueOf(1), myBetterService.callMeMaybe());
		} catch (Exception e) {
			fail("should not fail due to " + e);
		}
		
		try {
			MyService mySuxyService = timeLimiter.newProxy(new MySuxyService(), MyService.class, 2, TimeUnit.SECONDS);
			mySuxyService.callMeMaybe();
			fail("should fail due to timeout");
		} catch (Exception e) {
			assertTrue(e instanceof UncheckedTimeoutException);
		}
		
	}
	
	public static interface MyService {
		public Integer callMeMaybe() throws Exception;
	}

	public static class MyBetterService implements MyService {
		@Override
		public Integer callMeMaybe() throws Exception {
			return Integer.valueOf(1);
		}
	}
	
	public static class MySuxyService implements MyService {
		@Override
		public Integer callMeMaybe() throws Exception {
			Thread.sleep(TimeUnit.SECONDS.toMillis(5));
			return Integer.valueOf(0);
		}
		
	}
}
