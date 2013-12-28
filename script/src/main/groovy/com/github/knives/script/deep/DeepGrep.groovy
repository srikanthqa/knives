package com.github.knives.script.deep
import org.apache.commons.vfs2.FileContent
import org.apache.commons.vfs2.FileName
import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.FileSystemManager
import org.apache.commons.vfs2.VFS
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.github.knives.script.asm.ClassDecompiler

public class DeepGrep {
	final private static Logger LOG = LoggerFactory.getLogger(DeepGrep.class)
	
	final private static def CLASS_EXTENSION = 'class'

	final private def paths
	final private def keywords
	final private FileSystemManager fsManager = VFS.getManager()
	
	public DeepGrep(def paths, def keywords) {
		this.paths = paths
		this.keywords = keywords
	}

	public void printSearchDetails() {
		paths.each { handleRawPath(it) }
	}
	
	private void handleRawPath(final String path) {
		final def localFile = new File(path)
		final FileObject fileObject = fsManager.toFileObject(localFile)
		final def stack = [fileObject] as LinkedList<FileObject>
		
		while (stack.isEmpty() == false) {
			final FileObject currentFileObject = stack.removeLast()
			if (currentFileObject.exists() && currentFileObject.isReadable()) {
				
				if (match(currentFileObject)) {
					print(currentFileObject)
				}
				
				final FileObject wrappedFileObject = DeepUtil.wrapFileObject(fsManager, currentFileObject)
				
				if (wrappedFileObject.getType().hasChildren()) {
					stack.addAll(wrappedFileObject.getChildren())
				}
			}
		}
	}
	
	public boolean match(final FileObject fileObject) {
		final FileName fileName = fileObject.getName()
		
		if (fileName.getPath().matches(keywords)) return true
		if (fileName.getBaseName().matches(keywords)) return true
		if (fileName.getExtension().equalsIgnoreCase(keywords)) return true
		return false
	}

	public void print(final FileObject fileObject) {
		final FileName fileName = fileObject.getName()
		
		if (fileName.getExtension() == CLASS_EXTENSION) {
			printClassDetail(fileObject)
		} else {
			printNonClassDetail(fileObject)
		}
	}
	
	private void printNonClassDetail(final FileObject fileObject) {
		final FileName fileName = fileObject.getName()
		println fileName.getURI()
	}
	
	private void printClassDetail(final FileObject fileObject) {
		final FileName fileName = fileObject.getName()
		final FileContent fileContent = fileObject.getContent()
		
		println fileName.getURI()
		
		try {
			final ClassReader reader = new ClassReader(fileContent.getInputStream().getBytes())
			
			final ClassNode classNode = new ClassNode()
			reader.accept(classNode, 0)
			
			new ClassDecompiler().decompile(classNode);
			
			println()
		} catch (Exception ignore) { println "Unable to read class file" }
	}
	
	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'DeepGrep -n <name> [path/to/jars|directory|file]')
		cli.h( longOpt: 'help', required: false, 'show usage information' )
		cli.n( longOpt: 'name', args: 1, required: true, 'search class by name')

		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h) {
			cli.usage();
			return
		}

		def keywords = opt.n
		def paths = opt.arguments()
		new DeepGrep(paths, keywords).printSearchDetails()
	}
}