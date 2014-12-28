package com.github.knives.build

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class StopExternalProcess extends DefaultTask {
	@Input
	AbstractStartExternalProcess startTask
	
	@TaskAction
	void run() {
		
	}
}
