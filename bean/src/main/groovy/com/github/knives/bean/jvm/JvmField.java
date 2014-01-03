package com.github.knives.bean.jvm;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class JvmField {
	final private List<JvmAnnotation> annotations;
	final private Set<JvmKeyword> modifiers;
	final private String type;
	final private String name;
	
	private JvmField(Set<JvmKeyword> modifiers, String type, String name) {
		this.modifiers = modifiers;
		this.type = type;
		this.name = name;
	}
	
	public Set<JvmKeyword> getModifiers() {
		return modifiers;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public static JvmFieldBuilder create() {
		return new JvmFieldBuilder();
	}
	
	public static class JvmFieldBuilder {
		private Set<JvmKeyword> modifiers;
		private String type;
		private String name;
		
		private JvmFieldBuilder() { }
		
		public JvmFieldBuilder name(String name) {
			this.name = name;
			return this;
		}
	
		public JvmFieldBuilder modifiers(JvmKeyword first, JvmKeyword ... rest) {
			if (rest.length == 0) {
				this.modifiers = EnumSet.of(first);
			} else {
				this.modifiers = EnumSet.of(first, rest);
			}
			
			return this;
		}
		
		public JvmFieldBuilder type(String type) {
			this.type = type;
			return this;
		}
		
		public JvmField build() {
			if (name == null) throw new IllegalArgumentException("name cannot be null");
			if (type == null) throw new IllegalArgumentException("type cannot be null");
			if (modifiers == null) modifiers = EnumSet.noneOf(JvmKeyword.class);
			
			return new JvmField(modifiers, type, name);
		}		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((modifiers == null) ? 0 : modifiers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		JvmField other = (JvmField) obj;
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
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "JvmField [modifiers=" + modifiers + ", type=" + type
				+ ", name=" + name + "]";
	}
}
