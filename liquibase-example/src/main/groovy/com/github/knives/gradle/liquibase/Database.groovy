package com.github.knives.gradle.liquibase

class Database {
	def name
	def url
	def username
	def password
	
	Database(String name) {
		this.name = name
	}
}
