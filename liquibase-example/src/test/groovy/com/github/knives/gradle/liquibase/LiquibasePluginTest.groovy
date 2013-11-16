package com.github.knives.gradle.liquibase

import static org.junit.Assert.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class LiquibasePluginTest {
	@Test
	public void addLiquibasePlugin() {
		final Project project = ProjectBuilder.builder().build()
		project.with {
			apply plugin: 'liquibase'
			
			liquibase {
				changelogs {
					main {
						
					}	
				}
				
				databases {
					sandbox {
						url = 'jdbc:h2:db/liquibase_workshop'
						username = 'sa'
						password = ''
					}
					
					staging {
						url = 'jdbc:mysql://staging.server/app_db'
						username = 'dev_account'
						password = 'abc'
					}
				}
				
				defaultDatabase = databases.sandbox
			}
			
			task('test', type: LiquibaseTask) {
				group = 'Liquibase'
				command = 'test'
			}
		}
		
		
		assertTrue(project.tasks.generateChangeLog instanceof LiquibaseTask)
		assertTrue(project.tasks.changeLogSync instanceof LiquibaseTask)
		assertTrue(project.tasks.update instanceof LiquibaseTask)
		assertTrue(project.tasks.test instanceof LiquibaseTask)
		
		assertTrue(project.liquibase instanceof LiquibaseExtension)
		assertTrue(project.liquibase.defaultDatabase instanceof Database)
		assertTrue(project.liquibase.databases.sandbox instanceof Database)
		assertTrue(project.liquibase.databases.staging instanceof Database)
		assertTrue(project.liquibase.changelogs.main instanceof ChangeLog)
	}
}
