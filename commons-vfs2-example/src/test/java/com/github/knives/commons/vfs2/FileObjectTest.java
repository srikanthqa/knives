package com.github.knives.commons.vfs2;

import java.util.Arrays;

import org.apache.commons.vfs2.FileDepthSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;

public class FileObjectTest {

	@Test
	public void test() throws Exception {
		FileSystemManager fileSystemManager = VFS.getManager();
		FileObject root = fileSystemManager.resolveFile("/tmp/commons-vfs2test");
		System.out.println(root.getName().getFriendlyURI());

		tranverse(fileSystemManager, root);
	}
	
	public void tranverse(FileSystemManager fileSystemManager, FileObject root) throws Exception {
		FileObject[] descendents = root.findFiles(new FileDepthSelector(1, 1));
		Arrays.stream(descendents).forEach((FileObject file) -> {
			try {
				if (fileSystemManager.canCreateFileSystem(file)) {
					file = fileSystemManager.createFileSystem(file);
				}

				System.out.println(file.getName().getFriendlyURI());
				
				if (file.getType().hasChildren()) {
					tranverse(fileSystemManager, file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
