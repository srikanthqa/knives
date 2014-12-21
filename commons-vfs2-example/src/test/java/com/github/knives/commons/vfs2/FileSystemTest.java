package com.github.knives.commons.vfs2;

import static org.junit.Assert.*;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.junit.Ignore;
import org.junit.Test;

public class FileSystemTest {

	@Test
	@Ignore
	public void testJar() throws FileSystemException {
		FileSystemManager fileSystemManager = VFS.getManager();
		FileSystemOptions opts = new FileSystemOptions(); 
		// resolveFile
		FileObject root = fileSystemManager.resolveFile("jar:res:testjar.jar!/org/objenesis/ObjenesisHelper.java", opts);
		System.out.println(root.getName().getFriendlyURI());
	}

}
