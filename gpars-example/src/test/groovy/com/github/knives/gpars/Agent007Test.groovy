package com.github.knives.gpars

import groovyx.gpars.agent.Agent

import java.util.concurrent.CyclicBarrier

import org.junit.Test

class Agent007Test {

	/**
	 * With the commands being executed serially the commands do not need to 
	 * care about concurrency and can assume the data is all theirs when run. 
	 * Although implemented differently, GPars Agents, called Agent , 
	 * fundamentally behave like actors. They accept messages and process them asynchronously. 
	 * The messages, however, must be commands (functions or Groovy closures) and will 
	 * be executed inside the agent. 
	 * 
	 * Essentially, agents safe-guard mutable values by allowing only a single agent-managed 
	 * thread to make modifications to them. The mutable values are not directly accessible 
	 * from outside, but instead requests have to be sent to the agent and the agent 
	 * guarantees to process the requests sequentially on behalf of the callers. Agents
	 *  guarantee sequential execution of all requests and so consistency of the values.
	 */
	@Test
	void testAgent007() {
		
		def jugMembers = new Agent<List<String>>(['Me'])  //add Me
		
		jugMembers.send { it.add 'James'}  //add James
		
		CyclicBarrier barrier = new CyclicBarrier(2);
		
		final Thread t1 = Thread.start {
			barrier.await()
			jugMembers.send {it.add 'Joe'}  //add Joe
		}
		
		final Thread t2 = Thread.start {
			barrier.await()
			jugMembers << {it.add 'Dave'}  //add Dave
			jugMembers {it.add 'Alice'}    //add Alice (using the implicit call() method)
		}
		
		[t1, t2]*.join()
		println jugMembers.val
		jugMembers.valAsync {println "Current members: $it"}
		
		jugMembers.await()
	}
}
