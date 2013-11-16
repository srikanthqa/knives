package com.github.knives.gradle.liquibase;

import org.gradle.api.Plugin
import org.gradle.api.Project

public class LiquibasePlugin implements Plugin<Project> {

	final static def GROUP = 'Liquibase'
	
	@Override
	public void apply(final Project project) {
		def databases = project.container(Database)
		def changelogs = project.container(ChangeLog)
		
		project.extensions.create('liquibase', LiquibaseExtension, databases, changelogs)
		
		def taskLiquibaseCommand = [
			'generateChangeLog', 'changeLogSync', 'update'
		]
		
		taskLiquibaseCommand.each { liquibaseCommand ->
			project.task(liquibaseCommand, type: LiquibaseTask, group: LiquibasePlugin.GROUP) {
				command = liquibaseCommand
			}
		}
	}

}
