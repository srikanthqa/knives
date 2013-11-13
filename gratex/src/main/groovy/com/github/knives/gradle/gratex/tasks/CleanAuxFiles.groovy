package com.github.knives.gradle.gratex.tasks

import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskAction

class CleanAuxFiles extends Delete {

	@TaskAction
	public void clean() {
		delete()
	}
}
