package com.github.knives.gradle.gratex

import org.gradle.api.Plugin
import org.gradle.api.Project

import com.github.knives.gradle.gratex.source.DocSourceSet
import com.github.knives.gradle.gratex.source.DocSourceSetFactory
import com.github.knives.gradle.gratex.tasks.CleanAuxFilesTask
import com.github.knives.gradle.gratex.tasks.TranspileDocTask

class GratexPlugin implements Plugin<Project> {
	final static def GROUP = 'Gratex'
	
	@Override
	public void apply(final Project project) {
		def docSourceSetFactory = new DocSourceSetFactory(project)
		def docSourceSetsContainer = project.container(DocSourceSet, docSourceSetFactory) 

		project.task(TranspileDocTask.NAME, type: TranspileDocTask, group: GratexPlugin.GROUP)
		project.task(CleanAuxFilesTask.NAME, type: CleanAuxFilesTask, group: GratexPlugin.GROUP)
		
		project.extensions.create(GratexExtension.NAME, GratexExtension, docSourceSetsContainer)
	}
}
