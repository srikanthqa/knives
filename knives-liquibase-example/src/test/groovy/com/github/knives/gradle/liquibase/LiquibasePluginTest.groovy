package com.github.knives.gradle.liquibase

import static org.junit.Assert.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class LiquibasePluginTest {
	@Test
	public void addLiquibasePlugin() {
		Project project = ProjectBuilder.builder().build()
		project.apply plugin: 'liquibase'
		
		assertTrue(project.tasks.generateChangeLog instanceof LiquibaseTask)
		assertTrue(project.tasks.changeLogSync instanceof LiquibaseTask)
		assertTrue(project.tasks.update instanceof LiquibaseTask)
		
		
	}
}
