package com.github.knives.script.asm
/**
 * Copy and paste with minor tweak to show example how to use asm tree api
 * http://www.geekyarticles.com/2011/10/manipulating-java-class-files-with-asm_13.html
 */

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode

final def cli = new CliBuilder(usage: 'AsmTreeClassReader [path/to/file.class]')
cli.h( longOpt: 'help', required: false, 'show usage information' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

opt.arguments().each { 
	final File file = new File(it)
	if (file.exists() && file.isDirectory() == false) {
		ClassReader cr=new ClassReader(file.getBytes());
		ClassNode classNode=new ClassNode();
		
		//ClassNode is a ClassVisitor
		cr.accept(classNode, 0);
		
		//Let's move through all the methods
		classNode.methods.each { MethodNode methodNode ->
			println methodNode.name + "  " + methodNode.desc
		}
	}
}

