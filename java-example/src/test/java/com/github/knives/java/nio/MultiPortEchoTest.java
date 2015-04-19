package com.github.knives.java.nio;

import org.junit.Test;

public class MultiPortEchoTest {
	@Test
	public void testMultiportEcho() throws Exception {
		MultiPortEcho.main(new String[] {"9999", "10000", "10001"});
	}
}
