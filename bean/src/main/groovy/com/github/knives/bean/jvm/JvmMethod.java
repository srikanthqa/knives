package com.github.knives.bean.jvm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.list.UnmodifiableList;


public class JvmMethod {
	final private List<JvmAnnotation> annotations;
	final private Set<JvmKeyword> modifiers;
	final private String returnType;
	final private String name;
	final private String[] exceptionType;

	@SuppressWarnings("unchecked")
	private JvmMethod(List<JvmAnnotation> annotations, Set<JvmKeyword> modifiers, String returnType, String name, String[] exceptionType) {
		this.modifiers = modifiers;
		this.name = name;
		this.returnType = returnType;
		this.exceptionType = exceptionType;
		this.annotations = UnmodifiableList.decorate(annotations);
	}
	
	public List<JvmAnnotation> getAnnotations() {
		return annotations;
	}
	
	public Set<JvmKeyword> getModifiers() {
		return modifiers;
	}

	public String getReturnType() {
		return returnType;
	}
	public String getName() {
		return name;
	}

	public String[] getExceptionType() {
		return exceptionType;
	}	
	
	public static JvmMethodBuilder create() {
		return new JvmMethodBuilder();
	}
	
	public static class JvmMethodBuilder {
		private String name;
		private Set<JvmKeyword> modifiers;
		private String returnType;
		private String[] exceptionType;
		private List<JvmAnnotation> annotations = new ArrayList<JvmAnnotation>();
		private JvmMethodBuilder() { }
		
		public JvmMethodBuilder name(String name) {
			this.name = name;
			return this;
		}
	
		public JvmMethodBuilder modifiers(JvmKeyword first, JvmKeyword ... rest) {
			if (rest.length == 0) {
				this.modifiers = EnumSet.of(first);
			} else {
				this.modifiers = EnumSet.of(first, rest);
			}
			
			return this;
		}
		
		public JvmMethodBuilder returnType(String returnType) {
			this.returnType = returnType;
			return this;
		}
		
		public JvmMethodBuilder exceptionType(String[] exceptionType) {
			this.exceptionType = exceptionType;
			return this;
		}
		
		public JvmMethodBuilder annotate(JvmAnnotation annotation) {
			annotations.add(annotation);
			return this;
		}
		
		public JvmMethod build() {
			if (name == null) throw new IllegalArgumentException("name cannot be null");
			if (returnType == null) returnType = "void";
			if (modifiers == null) modifiers = EnumSet.noneOf(JvmKeyword.class);
			if (exceptionType == null) exceptionType = new String[]{};
			
			return new JvmMethod(annotations, modifiers, returnType, name, exceptionType);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annotations == null) ? 0 : annotations.hashCode());
		result = prime * result + Arrays.hashCode(exceptionType);
		result = prime * result
				+ ((modifiers == null) ? 0 : modifiers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((returnType == null) ? 0 : returnType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JvmMethod other = (JvmMethod) obj;
		if (annotations == null) {
			if (other.annotations != null)
				return false;
		} else if (!annotations.equals(other.annotations))
			return false;
		if (!Arrays.equals(exceptionType, other.exceptionType))
			return false;
		if (modifiers == null) {
			if (other.modifiers != null)
				return false;
		} else if (!modifiers.equals(other.modifiers))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		return true;
	}	
	
	@Override
	public String toString() {
		return "JvmMethod [modifiers=" + modifiers + ", returnType="
				+ returnType + ", name=" + name + ", exceptionType="
				+ Arrays.toString(exceptionType) + "]";
	}	
}
