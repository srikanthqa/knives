package com.github.knives.script.deep
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

import org.apache.commons.lang3.StringUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.AnnotationNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode

import com.github.knives.script.asm.ClassDecompiler
import com.github.knives.script.asm.ClassDecompiler.JavaModifier
import com.github.knives.script.virtualfile.VirtualFile
import com.github.knives.script.virtualfile.VirtualFileFactory
import com.github.knives.script.virtualfile.ZipEntryVirtualFile

public class DeepGrep {
	final private static def CLASS_EXTENSION = 'class'

	final private def paths
	final private def keywords

	public DeepGrep(def paths, def keywords) {
		this.paths = paths
		this.keywords = keywords
	}

	public void printSearchDetails() {
		paths.each { handleRawPath(it) }
	}
	
	private void handleRawPath(final String path) {
		final VirtualFile initialVirtualFile = VirtualFileFactory.createVirtualFile(path)
		final LinkedList<VirtualFile> toBeProcessedVirtualFiles = new LinkedList<VirtualFile>()
		toBeProcessedVirtualFiles.push(initialVirtualFile)
		
		while (toBeProcessedVirtualFiles.isEmpty() == false) {
			final VirtualFile processedVirtualFile = toBeProcessedVirtualFiles.removeFirst()
			
			if (processedVirtualFile.isContainer()) {
				toBeProcessedVirtualFiles.addAll(processedVirtualFile.getChildren())
			} else {
				if (match(processedVirtualFile)) {
					print(processedVirtualFile)
				}
			}
		}
	}
	
	public boolean match(final VirtualFile virtualFile) {
		if (virtualFile.getCanonicalPath().matches(keywords)) return true
		if (virtualFile.getBaseName().matches(keywords)) return true
		if (virtualFile.getExtension().equalsIgnoreCase(keywords)) return true
		return false
	}

	public void print(final VirtualFile virtualFile) {
		if (virtualFile.getExtension() == CLASS_EXTENSION) {
			printClassDetail(virtualFile)
		} else {
			printNonClassDetail(virtualFile)
		}
	}
	
	private void printNonClassDetail(final VirtualFile virtualFile) {
		println virtualFile.getCanonicalPath()
	}

	
	private void printClassDetail(final VirtualFile virtualFile) {
		println virtualFile.getCanonicalPath()
		try {
			final ClassReader reader = new ClassReader(virtualFile.getBytes())
			
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