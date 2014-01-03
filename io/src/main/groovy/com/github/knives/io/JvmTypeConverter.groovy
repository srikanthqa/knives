package com.github.knives.io

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

import com.github.knives.bean.jvm.JvmType

class JvmTypeConverter {
	public JvmType convert(File file) {
		final ClassReader reader = new ClassReader(file.getInputStream().getBytes())
		final ClassNode classNode = new ClassNode()
		reader.accept(classNode, 0)
		
		// TODO Auto-generated method stub
		return null;
	}
}
