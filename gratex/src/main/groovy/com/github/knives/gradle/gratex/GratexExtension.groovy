package com.github.knives.gradle.gratex

import org.gradle.api.NamedDomainObjectContainer

import com.github.knives.gradle.gratex.source.DocSourceSet

class GratexExtension {
	final static String NAME = 'gratex'
	
	final NamedDomainObjectContainer<DocSourceSet> sourceSets
	
	GratexExtension(sourceSets) {
		this.sourceSets = sourceSets
	}
	
	def sourceSets(Closure closure) {
		sourceSets.configure closure
	}
}
