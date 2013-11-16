package com.github.knives.gradle.gratex.tasks

import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskAction

class CleanAuxFilesTask extends Delete {
	final static def NAME = 'cleanAux'
	
	@TaskAction
	public void clean() {
		delete()
	}
}
