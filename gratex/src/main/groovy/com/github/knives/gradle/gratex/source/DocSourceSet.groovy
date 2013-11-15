package com.github.knives.gradle.gratex.source;

import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.tasks.TaskDependency

public class DocSourceSet implements Named {
	final private String name
	final private SourceDirectorySet doc
	
	public DocSourceSet(String name, SourceDirectorySet doc) {
		this.name = name
		this.doc = doc
	}
	
	@Override
	public String getName() {
		return name;
	}

	public SourceDirectorySet getDoc() {
		doc
	}
}
