package com.github.knives.gradle.gratex.tasks

import org.gradle.api.file.FileTree
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

class TranspileDocTask extends SourceTask {

	final static def NAME = 'transpileDoc' 
	
	@Input
	FileTree source
	
	@TaskAction
	public void compile() {
		
	}
}
