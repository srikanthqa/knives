package com.github.knives.gradle.gratex.source

import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.project.ProjectInternal

class DocSourceSetFactory implements NamedDomainObjectFactory<DocSourceSet> {

	final ProjectInternal project
	
	public DocSourceSetFactory(Project project) {
		this.project = (ProjectInternal)project
	}
	
	@Override
	public DocSourceSet create(String name) {
		final String docSourceDisplayName = String.format("%s Doc sources", name);
		final SourceDirectorySet doc = new DefaultSourceDirectorySet(docSourceDisplayName, project.getFileResolver())
		return new DocSourceSet(name, doc)
	}

}
