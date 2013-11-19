package com.github.knives.gradle.gratex

import static org.junit.Assert.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.github.knives.gradle.gratex.source.DocSourceSet

class GratexPluginTest {
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	private Project project
	
	@Before
	public void before() {
		project = ProjectBuilder.builder().withProjectDir(folder.root).build()
	}
	
	@Test
	public void testGratexPlugin() {
		final Project project = ProjectBuilder.builder().withProjectDir(folder.root).build()
		
		def srcDir = folder.newFolder("src", "main", "docs")
		// groovy does not create empty implicit
		def testFile = (new File(srcDir, 'test.tex') << "").canonicalFile
		
		project.with {
			apply plugin: 'gratex'
			
			gratex {
				sourceSets {
					main {
						docs {
							
						}
					}
				}
			}
		}
		
		assertTrue(project.gratex.sourceSets.main instanceof DocSourceSet)
		assertEquals("main", project.gratex.sourceSets.main.name)
		assertEquals([testFile], project.gratex.sourceSets.main.docs.files.toList())
	}
}

