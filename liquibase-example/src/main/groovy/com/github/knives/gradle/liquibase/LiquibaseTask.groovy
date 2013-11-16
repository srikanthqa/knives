package com.github.knives.gradle.liquibase

import groovy.transform.ToString
import liquibase.commandline.Main

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.tasks.TaskAction

@ToString(includeSuper = false, includeNames = true)
class LiquibaseTask extends DefaultTask {
	String command
	String url, password, username
	File changeLog
	
	private def initializeFromExtensionIfNecessary() {
		['url', 'username', 'password'].each {
			if (this."${it}" == null) {
				this."${it}" = project.liquibase.defaultDatabase."${it}"
			}
		}
	}  
	
	private def validateProperties() {
		this.properties.any { prop, value ->
			// exclude these property
			if(["metaClass","class"].any {it == prop}) {
				 return false
			}
			
			if (hasProperty(prop)) {
				if (value == null) {
					return true
				}
			}
			
			return false
		}
	}
	
	@TaskAction
	def liquibaseAction() {
		// only run the following after project is evaluated
		
		initializeFromExtensionIfNecessary()
		
		if (validateProperties()) {
			throw new StopExecutionException("Task is misconfigured " + this.toString())
		}

		println this.toString()
		
		/*
		def args = [
			"--url=${url}",
			"--password=${password}",
			"--username=${username}",
			"--changeLogFile=${changeLog.absolutePath}",
			command
		]
		
		Main.main(args as String[])
		*/
		
	}
}
