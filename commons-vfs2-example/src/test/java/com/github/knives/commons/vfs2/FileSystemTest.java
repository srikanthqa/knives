package com.github.knives.commons.vfs2;

import static org.junit.Assert.assertTrue;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;

public class FileSystemTest {

	@Test
	public void testJar() throws FileSystemException {
		FileSystemManager fileSystemManager = VFS.getManager();
		FileSystemOptions opts = new FileSystemOptions(); 
		
		FileObject resourceFile = fileSystemManager.resolveFile("res:testjar.jar");
		
		System.out.println(resourceFile.getName().getFriendlyURI());
		
		FileObject file = fileSystemManager.resolveFile(
				"jar:" + resourceFile.getName().getFriendlyURI() + "!/org/objenesis/ObjenesisHelper.java", opts);
		
		assertTrue(file.getName().getFriendlyURI() + " does not exist", file.exists());
	}

	@Test
	public void testTar() throws FileSystemException {
		FileSystemManager fileSystemManager = VFS.getManager();
		FileSystemOptions opts = new FileSystemOptions(); 
		
		FileObject resourceFile = fileSystemManager.resolveFile("res:testtar.tar");
		
		System.out.println(resourceFile.getName().getFriendlyURI());
		
		FileObject file = fileSystemManager.resolveFile(
				"tar:" + resourceFile.getName().getFriendlyURI() + "!/level1/level2.txt", opts);
		
		assertTrue(file.getName().getFriendlyURI() + " does not exist", file.exists());
	}
	
	@Test
	public void testZip() throws FileSystemException {
		FileSystemManager fileSystemManager = VFS.getManager();
		FileSystemOptions opts = new FileSystemOptions(); 
		
		FileObject resourceFile = fileSystemManager.resolveFile("res:testzip.zip");
		
		System.out.println(resourceFile.getName().getFriendlyURI());
		
		FileObject file = fileSystemManager.resolveFile(
				"zip:" + resourceFile.getName().getFriendlyURI() + "!/level1/level2.txt", opts);
		
		assertTrue(file.getName().getFriendlyURI() + " does not exist", file.exists());
	}

	@Test
	public void testTarGz() throws FileSystemException {
		FileSystemManager fileSystemManager = VFS.getManager();
		FileSystemOptions opts = new FileSystemOptions(); 
		
		FileObject resourceFile = fileSystemManager.resolveFile("res:testtargz.tar.gz");
		
		System.out.println(resourceFile.getName().getFriendlyURI());
		
		FileObject file = fileSystemManager.resolveFile(
				"tar:gz:" + resourceFile.getName().getFriendlyURI() + "!/testtargz.tar" + "!/level1/level2.txt", opts);
		
		assertTrue(file.getName().getFriendlyURI() + " does not exist", file.exists());
	}
}
