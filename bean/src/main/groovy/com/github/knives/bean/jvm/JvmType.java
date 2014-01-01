package com.github.knives.bean.jvm;

import java.util.HashSet;
import java.util.Set;

public class JvmType {
	final private Set<JvmKeyword> modifiers = new HashSet<JvmKeyword>();
	final private String name;
	
	public Set<JvmKeyword> getModifiers() {
		return modifiers;
	}

	public String getName() {
		return name;
	}

	public JvmType(String name) {
		this.name = name;
	}
}
