package com.github.knives.gpars

class StopWatch {
	static def time(name, clos) {
		final def beginTime = System.nanoTime()
		try {
			return clos.call()
		} finally {
			final def endTime = System.nanoTime()
			println(name + " taken: " +  ((endTime - beginTime) as double) / 10**9 + " second(s)")
		}
	}
}