package com.github.knives.gpars

import groovyx.gpars.activeobject.ActiveObject
import groovyx.gpars.activeobject.ActiveMethod
import org.junit.Test

class ActiveObjectTest {
	
	@ActiveObject
	static class Decryptor {
		@ActiveMethod
		def decrypt(String encryptedText) {
			return encryptedText.reverse()
		}
	
		@ActiveMethod
		def decrypt(Integer encryptedNumber) {
			return -1*encryptedNumber + 142
		}
	}
	
	@Test
	void testActiveObject() {
		final Decryptor decryptor = new Decryptor()
		def part1 = decryptor.decrypt(' noitcA ni yvoorG')
		def part2 = decryptor.decrypt(140)
		def part3 = decryptor.decrypt('noitide dn')
		
		print part1.get()
		print part2.get()
		println part3.get()
	}
}
