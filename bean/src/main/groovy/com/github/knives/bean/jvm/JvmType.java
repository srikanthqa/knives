package com.github.knives.bean.jvm;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class JvmType {
	final private Set<JvmKeyword> modifiers;
	final private String name;
	final private String extendedType;
	final private String[] interfacedType;
	
	final private List<JvmAnnotation> annotations;
	final private List<JvmMethod> methods;
	final private List<JvmField> fields;
	
	private JvmType(Set<JvmKeyword> modifiers, String name,  String extendedType, String[] interfacedType) {
		this.modifiers = modifiers;
		this.name = name;
		this.extendedType = extendedType;
		this.interfacedType = interfacedType;
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
		
		public JvmType build() {
			if (name == null) throw new IllegalArgumentException("name cannot be null");
			if (extendedType == null) extendedType = "java.lang.Object";
			if (modifiers == null) modifiers = EnumSet.of(JvmKeyword.CLASS);
			if (interfacedType == null) interfacedType = new String[]{};
			
			return new JvmType(modifiers, name, extendedType,interfacedType);
		}
	}
}
