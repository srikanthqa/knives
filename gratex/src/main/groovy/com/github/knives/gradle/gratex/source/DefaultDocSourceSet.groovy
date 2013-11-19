package com.github.knives.gradle.gratex.source;

import org.gradle.api.Action
import org.gradle.api.file.SourceDirectorySet
import org.gradle.util.ConfigureUtil

public class DefaultDocSourceSet implements DocSourceSet {
	final private String name
	final private SourceDirectorySet docs
	
	public DefaultDocSourceSet(String name, SourceDirectorySet docs) {
		this.name = name
		this.docs = docs
	}
	
	@Override
	public String getName() {
		name
	}

	@Override
	public SourceDirectorySet getDocs() {
		docs
	}
	
	@Override
	public DocSourceSet configure(final Closure closure) {
		ConfigureUtil.configure(closure, this, false)
	}

	@Override
	public SourceDirectorySet docs(final Closure configureClosure) {
		ConfigureUtil.configure(configureClosure, getDocs())
		getDocs()
	}
}
