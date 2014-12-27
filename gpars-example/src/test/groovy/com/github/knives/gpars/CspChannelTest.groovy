package com.github.knives.gpars

import groovy.transform.TupleConstructor
import groovyx.gpars.dataflow.DataflowReadChannel
import groovyx.gpars.dataflow.DataflowWriteChannel
import groovyx.gpars.dataflow.SyncDataflowQueue
import groovyx.gpars.group.DefaultPGroup
import groovyx.gpars.scheduler.ResizeablePool

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

import org.junit.Test

class CspChannelTest {

	@TupleConstructor
	static class Greeter implements Callable<String> {
		DataflowReadChannel names
		DataflowWriteChannel greetings
	
		@Override
		String call() {
			while(!Thread.currentThread().isInterrupted()) {
				String name = names.getVal()
				greetings << "Hello " + name
			}
		}
	}
	
	/**
	 * Does not works
	 */
	@Test
	void testCspChannel() {
		def group = new DefaultPGroup(new ResizeablePool(true))
		
		
		def inChannel = new SyncDataflowQueue()
		def outChannel = new SyncDataflowQueue()
		
		group.task new Greeter(inChannel, outChannel)
		
		inChannel << "Joe"
		inChannel << "Dave"
		
		println outChannel.val
		println outChannel.val
	}
}
