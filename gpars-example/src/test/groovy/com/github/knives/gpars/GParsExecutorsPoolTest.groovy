package com.github.knives.gpars

import groovyx.gpars.GParsExecutorsPool

import java.util.concurrent.ExecutorService

import org.junit.Test

class GParsExecutorsPoolTest {
	@Test
	void testExecutorService() {
		GParsExecutorsPool.withPool { ExecutorService executorService ->
			executorService << {println 'Inside parallel task'}
		}
	}
}
