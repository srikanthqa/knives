package com.github.knives.gradle.custom.sourceset;

import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.DefaultSourceDirectorySet;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.internal.reflect.Instantiator;

public class CustomSourceSetFactory implements NamedDomainObjectFactory<CustomSourceSet> {
	private final Instantiator instantiator;
	private final FileResolver fileResolver;
	private final String parentName;

	public CustomSourceSetFactory(Instantiator instantiator, FileResolver fileResolver, String parentName) {
		this.instantiator = instantiator;
		this.fileResolver = fileResolver;
		this.parentName = parentName;
	}

	@Override
	public CustomSourceSet create(String name) {
		SourceDirectorySet sourceDirectorySet = instantiator.newInstance(DefaultSourceDirectorySet.class, name, fileResolver);
		sourceDirectorySet.srcDir(String.format("src/%s/%s", parentName, CustomSourceSet.CUSTOM_SOURCE_NAME));
		return instantiator.newInstance(DefaultCustomSourceSet.class, name, sourceDirectorySet);
	}
}
