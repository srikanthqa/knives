package com.github.knives.java;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Model after the following blog
 * http://waelchatila.com/2006/01/13/1137143896635.html
 * 
 * more about shutdown hook
 * http://hellotojavaworld.blogspot.com/2010/11/runtimeaddshutdownhook.html
 * 
 * Improvement:
 * 1. Use CountDownLatch instead of volatile
 * 2. And properly wait for all thread to shutdown rather than the dodgy Thread.sleep
 */

public class CtrlC {
	
	static private class RunWhenShuttingDown extends Thread {
		
		final CountDownLatch shutdownSignal;
		final CountDownLatch doneSignal;
		
		public RunWhenShuttingDown(CountDownLatch shutdownSignal, CountDownLatch doneSignal) {
			this.shutdownSignal = shutdownSignal;
			this.doneSignal = doneSignal;
		}
		
		public void run() {
			System.out.println("Control-C caught. Shutting down...");
			shutdownSignal.countDown(); // signal all thread to shutdown
			
			// wait for all thread to signal back
			try {
				doneSignal.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch shutdownSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(1);
		
		Runtime.getRuntime().addShutdownHook(new RunWhenShuttingDown(shutdownSignal, doneSignal));
		
		// waiting for shutdown every 1 second, println running
		while(shutdownSignal.await(1, TimeUnit.SECONDS) == false) {
			System.out.println("running...");
		}
		
		// signaling shutdown hook thread
		doneSignal.countDown();
		
		System.out.println("Stopped running.");
	}
}