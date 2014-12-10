package com.github.knives.groovy.transform

import groovy.transform.Field

@Field def accessible = 'Yay'
def inaccessiable ='Nah!!!'

def printOut() {
	println accessible
	
	try {
		println inaccessiable
	} catch(e) {
		// should print out MissingPropertyException
		println e
	}
}

printOut()