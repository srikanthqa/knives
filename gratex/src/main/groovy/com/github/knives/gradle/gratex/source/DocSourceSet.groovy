package com.github.knives.gradle.gratex.source;

import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.file.SourceDirectorySet
import org.gradle.util.Configurable

public interface DocSourceSet extends Named, Configurable<DocSourceSet> {
	
    SourceDirectorySet getDoc()

    SourceDirectorySet doc(Action<SourceDirectorySet> action)
}
