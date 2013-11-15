package com.github.knives.gradle.gratex.source;

import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.file.SourceDirectorySet
import org.gradle.util.Configurable
import org.gradle.util.ConfigureUtil

public class DocSourceSet implements Named, Configurable<DocSourceSet> {
	final private String name
	final private SourceDirectorySet doc
	
	public DocSourceSet(String name, SourceDirectorySet doc) {
		this.name = name
		this.doc = doc
	}
	
	@Override
	public String getName() {
		name
	}

	public SourceDirectorySet getDoc() {
		doc
	}
	
	public SourceDirectorySet doc(Action<SourceDirectorySet> action) {
		action.execute(doc)
		doc
	}

	@Override
	public DocSourceSet configure(Closure closure) {
        ConfigureUtil.configure(closure, this, false)
	}
}
