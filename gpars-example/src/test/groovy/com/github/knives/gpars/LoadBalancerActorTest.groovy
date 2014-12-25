package com.github.knives.gpars

import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor

import org.junit.Test

class LoadBalancerActorTest {
	
	final class LoadBalancer extends DefaultActor {
		int workers = 0
		List taskQueue = []
		private static final QUEUE_SIZE_TRIGGER = 10
	
		void act() {
			loop {
				react { message ->
					switch (message) {
						case NeedMoreWork:
							if (taskQueue.size() == 0) {
								println 'No more tasks in the task queue. Terminating the worker.'
								reply new PinkSlip()
								workers -= 1
							} else reply taskQueue.remove(0)
							break
						case WorkToDo:
							taskQueue << message
							if ((workers == 0) || (taskQueue.size() >= QUEUE_SIZE_TRIGGER)) {
								println 'Need more workers. Starting one.'
								workers += 1
								new DemoWorker(this).start()
							}
					}
					println "Active workers=${workers}\tTasks in queue=${taskQueue.size()}"
				}
			}
		}
	}
	
	final class DemoWorker extends DefaultActor {
		private static final Random random = new Random()
	
		Actor balancer
	
		def DemoWorker(balancer) {
			this.balancer = balancer
		}
	
		void act() {
			// cann't request more work in the loop()
			// cuz reply after stop() cause exception
			// only request work in react()
			requestMoreWork()
			
			loop {
				react { message ->
					switch (message) {
						case WorkToDo:
							processMessage(it)
							requestMoreWork()
							break
						case PinkSlip: 
							stop()
							break
					}
				}
			}
	
		}
	
		private void processMessage(message) {
			synchronized(random) {
				Thread.sleep random.nextInt(5000)
			}
		}
		
		private void requestMoreWork() {
			this.balancer << new NeedMoreWork()
		}
	}
	
	
	final class WorkToDo {}
	final class NeedMoreWork {}
	final class PinkSlip {}
	
	@Test
	void testLoadBalancer() {
		final Actor balancer = new LoadBalancer().start()
		
		//produce tasks
		for(i in 1..20) {
			Thread.sleep 100
			balancer << new WorkToDo()
		}
		
		//produce tasks in a parallel thread
		Thread.start {
			for(i in 1..10) {
				Thread.sleep 1000
				balancer << new WorkToDo()
			}
		}
		
		balancer << new WorkToDo()
		balancer << new WorkToDo()
		System.in.read()
	}

}
