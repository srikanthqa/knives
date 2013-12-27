package com.github.knives.script.vfs

import org.apache.commons.vfs2.FileName
import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.FileSystemManager
import org.apache.commons.vfs2.VFS

class ScanVfs {
	final public static def ZIP_EXTENSIONS = ['zip', 'jar', 'war', 'ear', 'sar']
	
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
				if (currentFileName.getExtension() in ZIP_EXTENSIONS) {
					final def String aliasFileUri = 'zip:' + currentFileName.getFriendlyURI()
					final FileObject aliasFileObject = fsManager.resolveFile( aliasFileUri )
					
					queue.addAll(aliasFileObject.getChildren())
					
				} else if (currentFileObject.getType().hasChildren()) {
					queue.addAll(currentFileObject.getChildren())
				}
				

			}

		}
		
	}

}
