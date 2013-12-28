package com.github.knives.script.deep

import groovy.transform.ToString
import groovy.transform.TupleConstructor

import org.apache.commons.vfs2.FileName
import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.FileSystemManager
import org.apache.commons.vfs2.FileType
import org.apache.commons.vfs2.VFS

class DeepCompare {
	final private FileSystemManager fsManager = VFS.getManager()
	
	// pass in at least 2 paths
	public void compare(final def paths) {
		final def stacks = paths.collect {
			final def localFile = new File(it)
			final FileObject fileObject = fsManager.toFileObject(localFile)
			final def stack = [fileObject] as LinkedList<FileObject>
			return stack
		}
		
		while(true) {
			
			final def equality = stacks.collect { final LinkedList<FileObject> stack ->
				final FileObject fileObject = stack.peekFirst()
				final FileName fileName = fileObject.getName()
				final String name = fileName.getBaseName() + '.' + fileName.getExtension()
				final boolean readable = fileObject.isReadable()
				final FileObjectEqualies equalityCheck = new FileObjectEqualies(name: name, readable: readable)
								
				if (readable) {
					final FileObject wrappedFileObject = DeepUtil.wrapFileObject(fsManager, fileObject)
					final FileType fileType = wrappedFileObject.getType()
					
					equalityCheck.setCouldHaveChildren(fileType.hasChildren())
					equalityCheck.setCouldHaveContent(fileType.hasContent())
				}
				
				return equalityCheck
			}
			
			final def isAllEqual = equality.inject([match: true, lastEqualityCheck: equality.get(0)]) { acc, value ->
				if (acc.match) {
					if (acc.lastEqualityCheck.isSame(value)) {
						return [match: true, lastEqualityCheck: value]
					} else {
						return [match: false, lastEqualityCheck: value]
					}
				} else {
					return acc
				}
			}
			
			// cases where they are not match
			
			if (isAllEqual.match) {
				
			}
			
		}
		
	}
	
	@TupleConstructor
	@ToString
	private static class FileObjectEqualies {
		final String name
		final boolean readable
		
		boolean couldHaveChildren
		boolean couldHaveContent
		
		public boolean isSame(FileObjectEqualies other) {
			if (name != other.name) return false
			if (readable != other.readable) return false
			if (couldHaveChildren != other.couldHaveChildren) return false
			
			// is semi-directory, then skip the content check
			if (couldHaveChildren == true) return true
			
			if (couldHaveContent != other.couldHaveContent) return false
			
			return true
		}
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
