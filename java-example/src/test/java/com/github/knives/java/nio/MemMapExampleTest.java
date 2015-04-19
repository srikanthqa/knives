package com.github.knives.java.nio;

import static org.junit.Assert.*;

import org.junit.Test;

public class MemMapExampleTest {

	@Test
	public void test() throws Exception {
		MemMapExample.main(new String[] {"/tmp/test.txt"});
	}

}
