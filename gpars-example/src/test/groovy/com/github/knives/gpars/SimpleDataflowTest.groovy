package com.github.knives.gpars

import static groovyx.gpars.dataflow.Dataflow.task
import groovyx.gpars.dataflow.DataflowQueue
import groovyx.gpars.dataflow.DataflowVariable
import groovyx.gpars.dataflow.Dataflows

import org.junit.Test

class SimpleDataflowTest {

	@Test
	void testDataFlow() {
		final def x = new DataflowVariable()
		final def y = new DataflowVariable()
		final def z = new DataflowVariable()
		
		task {
			z << x.val + y.val
		}
		
		task {
			x << 10
		}
		
		task {
			y << 5
		}
		
		println "Result: ${z.val}"
	}
	
	@Test
	void testDataFlows() {
		final def df = new Dataflows()
		
		task {
			df.z = df.x + df.y
		}
		
		task {
			df.x = 10
		}
		
		task {
			df.y = 5
		}
		
		println "Result: ${df.z}"
	}
	
	@Test
	void testDataFlowQueue() {
		def words = ['Groovy', 'fantastic', 'concurrency', 'fun', 'enjoy', 'safe', 'GPars', 'data', 'flow']
		final def buffer = new DataflowQueue()
		
		task {
			for (word in words) {
				buffer << word.toUpperCase()  //add to the buffer
			}
		}
		
		for (i in 1..words.size()) println buffer.val  //read from the buffer in a loop
	}
}
