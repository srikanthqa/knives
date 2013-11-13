package com.github.knives.gradle.liquibase

class ChangeLog {
	def name
	def file
	def description
	
	ChangeLog(String name) {
		this.name = name
	}
}
