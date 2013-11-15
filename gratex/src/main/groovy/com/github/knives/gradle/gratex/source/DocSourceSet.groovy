package com.github.knives.gradle.gratex.source;

import groovy.lang.Closure;

import org.gradle.api.Named
import org.gradle.api.file.SourceDirectorySet
import org.gradle.util.Configurable

public interface DocSourceSet extends Named, Configurable<DocSourceSet> {
	public SourceDirectorySet getDoc()
	
	public void doc(final Closure configureClosure)
}
