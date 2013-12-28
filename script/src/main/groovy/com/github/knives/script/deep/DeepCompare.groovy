package com.github.knives.script.deep

import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.FileSystemManager
import org.apache.commons.vfs2.VFS

class DeepCompare {
	final private FileSystemManager fsManager = VFS.getManager()
	
	public void compare(final def paths) {
		
		final def stacks = paths.collect {
			final def localFile = new File(it)
			final FileObject fileObject = fsManager.toFileObject(localFile)
			final def stack = [fileObject] as LinkedList<FileObject>
			return stack
		}
		
		/*
		while (true) {
			
			final def equalies = stacks.collect { final LinkedList<VirtualFile> stack ->
				final VirtualFile virtualFile = stack.peekFirst()
				
				[isContainer: virtualFile.isContainer(),
				 md5: virtualFile.isContainer() ? Hash.md5(virtualFile.getBytes()) : null ]
			}
			
			break
		}
		*/
	}
	
	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'DeepCompare <path/to/dir|file> <path/to/dir|file>')
		cli.h( longOpt: 'help', required: false, 'show usage information' )

		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h || opt.arguments().size() != 2) {
			cli.usage();
			return
		}
		
		new DeepCompare().compare(opt.arguments())
	}
}
