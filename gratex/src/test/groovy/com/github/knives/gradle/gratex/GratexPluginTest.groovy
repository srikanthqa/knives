package com.github.knives.gradle.gratex

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class GratexPluginTest {

	@Test
	public void testGratexPlugin() {
		Project project = ProjectBuilder.builder().build()
		project.apply plugin: 'gratex'
	}
}
