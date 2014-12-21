package com.github.knives.ftpserver;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FtpServerTest {

	@Rule
	public TemporaryFolder tmpDir = new TemporaryFolder();
	
	@Test
	public void testFtpServer() throws Exception {
		final PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		final UserManager userManager = userManagerFactory.createUserManager();

		BaseUser adminUser = new BaseUser();
		adminUser.setName("admin");
		adminUser.setPassword("password");
		adminUser.setHomeDirectory(tmpDir.getRoot().getCanonicalPath());
		adminUser.setAuthorities(Arrays.asList(new WritePermission()));
		
		userManager.save(adminUser);

		
		final ListenerFactory factory = new ListenerFactory();
		factory.setPort(19000);

		// replace default listener
		final FtpServerFactory serverFactory = new FtpServerFactory();
		serverFactory.addListener("default", factory.createListener());
		
		serverFactory.setUserManager(userManager);
		
		final FtpServer server = serverFactory.createServer();

		server.start();
		
		TimeUnit.SECONDS.sleep(5);
		
		server.stop();
	}

}
