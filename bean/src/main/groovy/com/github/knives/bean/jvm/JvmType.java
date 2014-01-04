package com.github.knives.bean.jvm;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.list.UnmodifiableList;

public class JvmType {
	final private Set<JvmKeyword> modifiers;
	final private String name;
	final private String extendedType;
	final private String[] interfacedType;
	
	final private List<JvmAnnotation> annotations;
	final private List<JvmMethod> methods;
	final private List<JvmField> fields;
	
	@SuppressWarnings("unchecked")
	private JvmType(List<JvmAnnotation> annotations, Set<JvmKeyword> modifiers, String name, 
			String extendedType, String[] interfacedType, List<JvmMethod> methods, List<JvmField> fields) {
		this.annotations = UnmodifiableList.decorate(annotations);
		this.modifiers = modifiers;
		this.name = name;
		this.extendedType = extendedType;
		this.interfacedType = interfacedType;
		this.methods = UnmodifiableList.decorate(methods);
		this.fields = UnmodifiableList.decorate(fields);
	}
	
	public Set<JvmKeyword> getModifiers() {
		return modifiers;
	}
	
	public String getName() {
		return name;
	}
	
	public String getExtendedType() {
		return extendedType;
	}
	
	public String[] getInterfacedType() {
		return interfacedType;
	}
	
	public JvmTypeBuilder create() {
		return new JvmTypeBuilder();
	}
	
	public static class JvmTypeBuilder {
		private Set<JvmKeyword> modifiers;
		private String name;
		private String extendedType;
		private String[] interfacedType;
		private List<JvmAnnotation> annotations = new ArrayList<JvmAnnotation>();
		private List<JvmMethod> methods = new ArrayList<JvmMethod>();
		private List<JvmField> fields = new ArrayList<JvmField>();
		
		private JvmTypeBuilder() { }
		
		public JvmTypeBuilder name(String name) {
			this.name = name;
			return this;
		}
	
		public JvmTypeBuilder modifiers(JvmKeyword first, JvmKeyword ... rest) {
			if (rest.length == 0) {
				this.modifiers = EnumSet.of(JvmKeyword.CLASS, first);
			} else {
				this.modifiers = EnumSet.of(first, rest);
			}
			
			return this;
		}
		
		public JvmTypeBuilder extendedType(String extendedType) {
			this.extendedType = extendedType;
			return this;
		}
		
		public JvmTypeBuilder interfacedType(String[] interfacedType) {
			this.interfacedType = interfacedType;
			return this;
		}
		
		public JvmTypeBuilder annotate(JvmAnnotation annotation) {
			annotations.add(annotation);
			return this;
		}
		
		public JvmTypeBuilder appendMethod(JvmMethod method) {
			methods.add(method);
			return this;
		}
		
		public JvmTypeBuilder appendField(JvmField field) {
			fields.add(field);
			return this;
		}
		
		public JvmType build() {
			if (name == null) throw new IllegalArgumentException("name cannot be null");
			if (extendedType == null) extendedType = "java.lang.Object";
			if (modifiers == null) modifiers = EnumSet.of(JvmKeyword.CLASS);
			if (interfacedType == null) interfacedType = new String[]{};
			
			return new JvmType(annotations, modifiers, name, extendedType, interfacedType, methods, fields);
		}
	}
}
