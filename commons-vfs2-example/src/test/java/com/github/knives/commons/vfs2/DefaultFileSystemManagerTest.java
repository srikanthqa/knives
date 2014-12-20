package com.github.knives.commons.vfs2;

import static org.junit.Assert.*;

import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;

public class DefaultFileSystemManagerTest {

	@Test
	public void testInit() throws Exception {
		FileSystemManager fileSystemManager = VFS.getManager();
	}

}
