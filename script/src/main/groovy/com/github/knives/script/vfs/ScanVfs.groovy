package com.github.knives.script.vfs

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
		
		
		final FileSystemManager fsManager = VFS.getManager()
		// TODO: hack add in file:// because gradle screw up mainExec
		// cannot execute the following command
		// gradle "mainExec com.github.knives.script.vfs.ScanVfs file:///tmp"
		final FileObject fileObject = fsManager.resolveFile( 'file://' + opt.arguments().get(0) )
		final def queue = [fileObject] as LinkedList<FileObject>
		
		while (queue.isEmpty() == false) {
			final FileObject currentFileObject = queue.remove()
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
					queue.addAll(wrappedFileObject.getChildren())
				}
			}
		}
	}
}
