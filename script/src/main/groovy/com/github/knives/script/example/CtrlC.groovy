package com.github.knives.script.example
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import groovy.transform.Canonical

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
	@Canonical
	static private class RunWhenShuttingDown extends Thread {
		final CountDownLatch shutdownSignal
		final CountDownLatch doneSignal
		
		public void run() {
			println "Control-C caught. Shutting down..."
			shutdownSignal.countDown() // signal all thread to shutdown
			
			// wait for all thread to signal back
			doneSignal.await()
		}
	}
	
	public static void main(String[] args) {
		final CountDownLatch shutdownSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(1);
		
		Runtime.getRuntime().addShutdownHook(new RunWhenShuttingDown(shutdownSignal, doneSignal))
		
		// waiting for shutdown every 1 second, println running
		while(shutdownSignal.await(1, TimeUnit.SECONDS) == false) {
			println "running..."
		}
		
		// signaling shutdown hook thread
		doneSignal.countDown()
		
		println "Stopped running."
	}
}