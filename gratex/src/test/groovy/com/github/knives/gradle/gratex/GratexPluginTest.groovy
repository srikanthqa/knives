package com.github.knives.gradle.gratex

import static org.junit.Assert.*

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import com.github.knives.gradle.gratex.source.DocSourceSet

class GratexPluginTest {

	@Test
	public void testGratexPlugin() {
		final Project project = ProjectBuilder.builder().build()
		project.apply plugin: 'gratex'
		
		project.gratex {
				sourceSets {
					main {
						doc {
							srcDir 'src/main/md'
						}
					}
				}
			}
		
		
		assertTrue(project.gratex.sourceSets.main instanceof DocSourceSet)
	}
}
