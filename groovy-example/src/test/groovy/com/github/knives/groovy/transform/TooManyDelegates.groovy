package com.github.knives.groovy.transform

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @Delegate annotation
 * 
 * All public instance methods present in the type of the annotated field 
 * and not present in the owner class will be added to owner class at compile time. 
 * 
 * The implementation of such automatically added methods is code which calls 
 * through to the delegate as per the normal delegate pattern.
 */

class LockableList {
	@Delegate private List list = []
	@Delegate private Lock lock = new ReentrantLock()
}

def list = new LockableList()

list.lock()
try {
	list << 'Groovy'
	list << 'Grails'
	list << 'Griffon'
} finally {
	list.unlock()
}

assert list.size() == 3
assert list instanceof Lock
assert list instanceof List