package com.github.knives.io

import org.apache.commons.lang3.StringUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.Type
import org.objectweb.asm.tree.AnnotationNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode

import com.github.knives.bean.jvm.JvmAnnotation
import com.github.knives.bean.jvm.JvmConstants
import com.github.knives.bean.jvm.JvmField
import com.github.knives.bean.jvm.JvmKeyword;
import com.github.knives.bean.jvm.JvmMethod
import com.github.knives.bean.jvm.JvmType
import com.github.knives.bean.jvm.JvmField.JvmFieldBuilder
import com.github.knives.bean.jvm.JvmMethod.JvmMethodBuilder
import com.github.knives.bean.jvm.JvmType.JvmTypeBuilder

class JvmTypeConverter {
	
	public JvmType convert(File file) {
		return convert(file.newInputStream())
	}
	
	public JvmType convert(InputStream inputStream) {
		final ClassReader reader = new ClassReader(inputStream)
		final ClassNode classNode = new ClassNode()
		reader.accept(classNode, 0)
		
		return buildJvmType(classNode)
	}

	private JvmType buildJvmType(final ClassNode classNode) {
		final JvmTypeBuilder jvmTypeBuilder = JvmType.create()
		
		jvmTypeBuilder.name(Type.getObjectType(classNode.name).getClassName())
		
		final String extendedType = classNode.superName
		
		if (StringUtils.isBlank(extendedType) == false) {
			final String fullExtendedType = Type.getObjectType(extendedType).getClassName() 
			if (JvmConstants.BASE_OBJECT != fullExtendedType) {
				jvmTypeBuilder.extendedType(fullExtendedType)
			}
		}
		
		if (classNode.interfaces != null && classNode.interfaces.size() > 0) {
			jvmTypeBuilder.interfacedType( (classNode.interfaces.collect { Type.getObjectType(it).getClassName() }) as String[] )
		}
		
		buildAnnotations(classNode.visibleAnnotations) {
			jvmTypeBuilder.annotate(it)
		}
		
		JvmKeyword.translateAccessToTypeKeywords(classNode.access).each {
			jvmTypeBuilder.modifiers(it)
		}
		
		buildJvmField(classNode).each {
			jvmTypeBuilder.appendField(it)
		}
		
		buildJvmMethod(classNode).each {
			jvmTypeBuilder.appendMethod(it)
		}
		
		return jvmTypeBuilder.build()
	}
	
	private void buildAnnotations(List<AnnotationNode> annotations, Closure callback) {
		annotations.each {
			final JvmAnnotation annotation = JvmAnnotation.create()
				.name(Type.getObjectType(it).getClassName())
				.build()
			
			callback(annotation)	
		}
	}
	
	private List<JvmMethod> buildJvmMethod(final ClassNode classNode) {
		return classNode.methods.collect {
			JvmMethodBuilder jvmMethodBuilder = JvmMethod.create()
			
			jvmMethodBuilder.build()
		}
	}
	
	private List<JvmField> buildJvmField(final ClassNode classNode) {
		return classNode.fields.collect { final FieldNode field ->
			final JvmFieldBuilder jvmFieldBuilder = JvmField.create()
				.type(Type.getObjectType(field.desc).getClassName())
				.name(field.name)
			
			JvmKeyword.translateAccessToFieldKeywords(field.access).each {
				jvmFieldBuilder.modifiers(it)
			}
			
			buildAnnotations(field.visibleAnnotations) {
				jvmFieldBuilder.annotate(it)
			}
			
			jvmFieldBuilder.build()
		}
	}
}
