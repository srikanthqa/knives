package com.github.knives.gpars

class StopWatch {
	static def withTimeRecording(clos) {
		final def beginTime = System.nanoTime()
		try {
			return clos.call()
		} finally {
			final def endTime = System.nanoTime()
			println("Taken: " +  ((endTime - beginTime) as double) / 10**9 + " second(s)")
		}
	}
}