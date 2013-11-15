package com.github.knives.gradle.gratex

import org.gradle.api.Plugin
import org.gradle.api.Project

import com.github.knives.gradle.gratex.source.DocSourceSet
import com.github.knives.gradle.gratex.source.DocSourceSetFactory

class GratexPlugin implements Plugin<Project> {

	@Override
	public void apply(final Project project) {
		def docSourceSetFactory = new DocSourceSetFactory(project)
		def docSourceSetsContainer = project.container(DocSourceSet, docSourceSetFactory) 

		project.extensions.create(GratexExtension.NAME, GratexExtension, docSourceSetsContainer)
	}
}
