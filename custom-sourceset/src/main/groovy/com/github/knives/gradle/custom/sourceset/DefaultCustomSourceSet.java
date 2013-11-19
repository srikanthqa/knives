package com.github.knives.gradle.custom.sourceset;

import groovy.lang.Closure;

import org.gradle.api.Action;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.TaskDependency;
import org.gradle.util.ConfigureUtil;

public class DefaultCustomSourceSet implements CustomSourceSet {
	final private SourceDirectorySet source;
	final private String name;
	
	public DefaultCustomSourceSet(String name, SourceDirectorySet source) {
		this.source = source;
		this.name = name;
	}
	
	@Override
	public SourceDirectorySet getSource() {
		return source;
	}

	@Override
	public void source(Action<SourceDirectorySet> action) {
		action.execute(source);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public TaskDependency getBuildDependencies() {
		return getSource().getBuildDependencies();
	}

	/*
	@Override
	public SourceDirectorySet configure(Closure closure) {
		return ConfigureUtil.configure(closure, getSource(), false);
	}
	*/
}
