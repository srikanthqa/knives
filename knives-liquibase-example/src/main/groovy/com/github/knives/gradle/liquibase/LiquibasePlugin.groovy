package com.github.knives.gradle.liquibase;

import org.gradle.api.Plugin
import org.gradle.api.Project

public class LiquibasePlugin implements Plugin<Project> {

	final static def GROUP = 'Liquibase'
	
	@Override
	public void apply(final Project project) {
		project.task('generateChangeLog', type: LiquibaseTask, group: LiquibasePlugin.GROUP) {
			command = generateChangeLog
		}
		
		project.task('changeLogSync', type: LiquibaseTask, group: LiquibasePlugin.GROUP) {
			command = 'changeLogSync'
		}
		
		project.task('update', type: LiquibaseTask, group: LiquibasePlugin.GROUP) {
			command = 'update'
		}
	}

}
