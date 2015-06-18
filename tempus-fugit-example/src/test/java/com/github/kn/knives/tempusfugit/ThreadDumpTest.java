package com.github.kn.knives.tempusfugit;

import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ThreadDump;

public class ThreadDumpTest {

	@Test
	public void test() {
		ThreadDump.dumpThreads(System.out);
	}

}
