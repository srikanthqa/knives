package com.github.knives.gpars

import static groovyx.gpars.dataflow.Dataflow.select
import groovy.transform.TupleConstructor
import groovyx.gpars.dataflow.DataflowReadChannel
import groovyx.gpars.dataflow.DataflowWriteChannel
import groovyx.gpars.dataflow.Select
import groovyx.gpars.dataflow.SyncDataflowQueue
import groovyx.gpars.group.DefaultPGroup
import groovyx.gpars.scheduler.ResizeablePool

import org.junit.Test

class CspAlternativeTest {
	@TupleConstructor
	class Receptionist implements Runnable {
		DataflowReadChannel emails
		DataflowReadChannel phoneCalls
		DataflowReadChannel tweets
		DataflowWriteChannel forwardedMessages
	
		//prioritySelect() would give highest precedence to phone calls
		private final Select incomingRequests = select([phoneCalls, emails, tweets])  
	
		@Override
		void run() {
			while(!Thread.currentThread().isInterrupted()) {
				String msg = incomingRequests.select()
				forwardedMessages << msg.toUpperCase()
			}
		}
	}
	
	@Test
	void testCspAlternative() {
		def group = new DefaultPGroup(new ResizeablePool(true))

		def a = new SyncDataflowQueue()
		def b = new SyncDataflowQueue()
		def c = new SyncDataflowQueue()
		def d = new SyncDataflowQueue()
		
		group.task new Receptionist(a, b, c, d)
		
		a << "my email"
		b << "my phone call"
		c << "my tweet"
		
		//The values come in random order since the process uses a Select to read its input
		3.times{
			println d.val.value
		}
	}
}
