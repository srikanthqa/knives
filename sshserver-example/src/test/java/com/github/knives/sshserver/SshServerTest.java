package com.github.knives.sshserver;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.apache.sshd.SshServer;



public class SshServerTest {

	@Test
	public void test() throws Exception {
		final SshServer sshd = SshServer.setUpDefaultServer();
		sshd.setPort(10080);

		sshd.start();
	}

}
