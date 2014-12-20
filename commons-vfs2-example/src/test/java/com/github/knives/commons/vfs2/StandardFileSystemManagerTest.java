package com.github.knives.commons.vfs2;

import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.junit.Test;

public class StandardFileSystemManagerTest {

	@Test
	public void testInit() throws Exception {
		StandardFileSystemManager fileSystemManager = new StandardFileSystemManager();
		// auto configure with providers.xml
		// auto configure with META-INF/vfs-providers.xml
		fileSystemManager.init();
	}
}
