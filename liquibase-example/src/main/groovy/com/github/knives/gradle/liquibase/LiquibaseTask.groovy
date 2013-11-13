package com.github.knives.gradle.liquibase

import liquibase.commandline.Main

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class LiquibaseTask extends DefaultTask {
	String command
	String url, password, username
	File changeLog
	
	@TaskAction
	def liquibaseAction() {
		def args = [
			"--url=${url}",
			"--password=${password}",
			"--username=${username}",
			"--changeLogFile=${changeLog.absolutePath}",
			command
			]
		
		Main.main(args as String[])
		
	}
}
