package com.github.knives.io

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

import com.github.knives.bean.jvm.JvmField
import com.github.knives.bean.jvm.JvmMethod
import com.github.knives.bean.jvm.JvmType
import com.github.knives.bean.jvm.JvmField.JvmFieldBuilder
import com.github.knives.bean.jvm.JvmMethod.JvmMethodBuilder
import com.github.knives.bean.jvm.JvmType.JvmTypeBuilder

class JvmTypeConverter {
	
	public JvmType convert(File file) {
		final ClassReader reader = new ClassReader(file.getInputStream().getBytes())
		final ClassNode classNode = new ClassNode()
		reader.accept(classNode, 0)
		
		return buildJvmType(classNode);
	}
	
	private JvmType buildJvmType(final ClassNode classNode) {
		final JvmTypeBuilder jvmTypeBuilder = JvmType.create()
		
		classNode.visibleAnnotations.each {
			
		}
		
		buildJvmField(classNode).each {
			jvmTypeBuilder.appendField(it)
		}
		
		buildJvmMethod(classNode).each {
			jvmTypeBuilder.appendMethod(it)
		}
		
		return jvmTypeBuilder.build()
	}
	
	private List<JvmMethod> buildJvmMethod(final ClassNode classNode) {
		return classNode.methods.collect {
			JvmMethodBuilder jvmMethodBuilder = JvmMethod.create()
			
			jvmMethodBuilder.build()
		}
	}
	
	private List<JvmField> buildJvmField(final ClassNode classNode) {
		return classNode.fields.collect {
			JvmFieldBuilder jvmFieldBuilder = JvmField.create()
			
			jvmMethodBuilder.build()
		}
	}
}
