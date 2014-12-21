package com.github.knives.commons.vfs2;

import java.util.Arrays;

import org.apache.commons.vfs2.FileDepthSelector;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;

public class FileObjectTest {

	@Test
	public void testLayerFileSystem() throws Exception {
		FileSystemManager fileSystemManager = VFS.getManager();
		FileSystemOptions opts = new FileSystemOptions(); 
		// resolveFile
		FileObject root = fileSystemManager.resolveFile("res:testtar.tar", opts);
		System.out.println(root.getName().getFriendlyURI());

		tranverse(fileSystemManager, root);
	}
	
	public void tranverse(FileSystemManager fileSystemManager, FileObject root) throws Exception {
		FileObject[] descendents = layerFileSystem(fileSystemManager, root).findFiles(new FileDepthSelector(1, 1));
		Arrays.stream(descendents).forEach((FileObject file) -> {
			try {
				file = layerFileSystem(fileSystemManager, file);
				
				System.out.println(file.getName().getFriendlyURI());
				
				if (file.getType().hasChildren()) {
					tranverse(fileSystemManager, file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private FileObject layerFileSystem(FileSystemManager fileSystemManager, FileObject file) throws FileSystemException {
		if (fileSystemManager.canCreateFileSystem(file)) {
			return fileSystemManager.createFileSystem(file);
		} else {
			return file;
		}
	}
}
