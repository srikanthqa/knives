package com.github.knives.gradle.liquibase

import org.gradle.api.NamedDomainObjectContainer

class LiquibaseExtension {
	final NamedDomainObjectContainer<Database> databases
	final NamedDomainObjectContainer<ChangeLog> changelogs
	Database defaultDatabase
	String context
	
	LiquibaseExtension(databases, changelogs) {
		this.databases = databases
		this.changelogs = changelogs
	}
	
	def databases(Closure closure) {
		databases.configure closure
	}
	
	def changelogs(Closure closure) {
		changelogs.configure closure
	}
	
}
