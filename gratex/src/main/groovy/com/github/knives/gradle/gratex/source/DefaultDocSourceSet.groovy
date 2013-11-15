package com.github.knives.gradle.gratex.source;

import org.gradle.api.Action
import org.gradle.api.file.SourceDirectorySet
import org.gradle.util.ConfigureUtil

public class DefaultDocSourceSet implements DocSourceSet {
	final private String name
	final private SourceDirectorySet doc
	
	public DefaultDocSourceSet(String name, SourceDirectorySet doc) {
		this.name = name
		this.doc = doc
	}
	
	@Override
	public String getName() {
		name
	}

	@Override
	public SourceDirectorySet getDoc() {
		doc
	}
	
	@Override
	public DocSourceSet configure(final Closure closure) {
		ConfigureUtil.configure(closure, this, false)
	}

	@Override
	public void doc(final Closure configureClosure) {
		doc.with configureClosure
	}
}
