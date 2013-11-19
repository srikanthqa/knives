package com.github.knives.gradle.gratex.source

import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import org.gradle.api.internal.project.ProjectInternal

class DocSourceSetFactory implements NamedDomainObjectFactory<DocSourceSet> {

	final private FileResolver fileResolver
	
	public DocSourceSetFactory(FileResolver fileResolver) {
		this.fileResolver = fileResolver
	}
	
	@Override
	public DocSourceSet create(String name) {
		final String docSourceDisplayName = String.format("%s doc sources", name);
		final SourceDirectorySet doc = new DefaultSourceDirectorySet(docSourceDisplayName, fileResolver)
		doc.srcDir("src/${name}/docs")
		return new DefaultDocSourceSet(name, doc)
	}

}
