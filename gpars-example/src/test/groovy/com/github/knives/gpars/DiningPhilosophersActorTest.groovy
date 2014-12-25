package com.github.knives.gpars

import groovyx.gpars.actor.DefaultActor

import org.junit.Test

class DiningPhilosophersActorTest {
	
	final class Philosopher extends DefaultActor {
		private Random random = new Random()
	
		String name
		def forks = []
	
		void act() {
			assert 2 == forks.size()
			loop {
				think()
				def replies = forks*.sendAndWait new Take(philosopher: this)
				
				if (replies.any {Rejected.isCase it}) {
					println "$name: \tOops, can't get my forks! Giving up."
					replies.eachWithIndex { reply, i -> if (Accepted.isCase(reply)) forks[i].send new Finished(philosopher: this) }
				} else {
					eat()
					forks*.send new Finished(philosopher: this)
				}
			}
		}
	
		void think() {
			println "$name: \tI'm thinking"
			Thread.sleep random.nextInt(2000)
			println "$name: \tI'm done thinking"
		}
	
		void eat() {
			println "$name: \tI'm EATING"
			Thread.sleep random.nextInt(2000)
			println "$name: \tI'm done EATING"
		}
	}
	
	final class Fork extends DefaultActor {
	
		String name
		boolean available = true
		Philosopher holdingPhilosopher
		
		void act() {
			loop {
				react {message ->
					switch (message) {
						case Take:
							if (available) {
								available = false
								holdingPhilosopher = (message as Take).philosopher
								reply new Accepted()
							} else reply new Rejected()
							break
						case Finished:
							assert !available
							assert (message as Finished).philosopher == holdingPhilosopher
							available = true
							break
						default: throw new IllegalStateException("Cannot process the message: $message")
					}
				}
			}
		}
	}
	
	final class Take {
		Philosopher philosopher
	}
	final class Accepted {}
	final class Rejected {}
	final class Finished {
		Philosopher philosopher
	}
	
	@Test
	void testDining() {
		def forks = [
			new Fork(name:'Fork 1'),
			new Fork(name:'Fork 2'),
			new Fork(name:'Fork 3'),
			new Fork(name:'Fork 4'),
			new Fork(name:'Fork 5')
		]

		def philosophers = [
			new Philosopher(name:'Joe', forks:[forks[0], forks[1]]),
			new Philosopher(name:'Dave', forks:[forks[1], forks[2]]),
			new Philosopher(name:'Alice', forks:[forks[2], forks[3]]),
			new Philosopher(name:'James', forks:[forks[3], forks[4]]),
			new Philosopher(name:'Phil', forks:[forks[4], forks[0]]),
		]

		forks*.start()
		philosophers*.start()

		System.in.read()
	}

}
