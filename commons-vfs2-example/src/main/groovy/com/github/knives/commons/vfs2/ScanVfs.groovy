package com.github.knives.commons.vfs2

import org.apache.commons.vfs2.FileName
import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.FileSystemManager
import org.apache.commons.vfs2.VFS

class ScanVfs {
	static main(args) {
		final def cli = new CliBuilder(usage: 'ScanVfs <url>')
		cli.h( longOpt: 'help', required: false, 'show usage information' )

		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h || opt.arguments().size() != 1) {
			cli.usage();
			return
		}
		
		final def fileName = opt.arguments().get(0)
		final def localFile = new File(fileName)
		if (localFile.exists() == false) {
			println "${fileName} does not exist"
			return
		}
		
		final FileSystemManager fsManager = VFS.getManager()
		final FileObject fileObject = fsManager.toFileObject(localFile)
		final def stack = [fileObject] as LinkedList<FileObject>
		
		while (stack.isEmpty() == false) {
			final FileObject currentFileObject = stack.removeLast()
			final FileName currentFileName = currentFileObject.getName()
			
			println currentFileName.getFriendlyURI()
			
			if (currentFileObject.isReadable()) {
				final FileObject wrappedFileObject = currentFileObject.with { final FileObject tmpFileObject ->
					if (fsManager.canCreateFileSystem(tmpFileObject)) {
						return fsManager.createFileSystem(tmpFileObject)
					} else {
						return tmpFileObject
					}
				}
				
				if (wrappedFileObject.getType().hasChildren()) {
					stack.addAll(wrappedFileObject.getChildren())
				}
			}
		}
	}
}
