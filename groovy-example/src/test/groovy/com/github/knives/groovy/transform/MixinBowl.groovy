package com.github.knives.groovy.transform

import org.junit.Test

/**
 * Category annotation can only apply on a single class unlike 'static method way'
 * However, you can still use @Category with 'use' keyword.
 * 
 * When use @Category, this refer to the enhance class
 * 
 * Furthermore, you can use @Mixin add @Category class method to the mixin class
 */

class MixinBowl {
	
	static interface Vehicle {
		String getName()
	}
	 
	@Category(Vehicle) 
	static class FlyingAbility {
		def fly() { "I'm the ${name} and I fly!" }
	}
	
	@Category(Vehicle) 
	static class DivingAbility {
		def dive() { "I'm the ${name} and I dive!" }
	}
	
	@Mixin(DivingAbility)
	static class Submarine implements Vehicle {
		String getName() { "Yellow Submarine" }
	}
	
	@Mixin(FlyingAbility)
	static class Plane implements Vehicle {
		String getName() { "Concorde" }
	}
	
	@Mixin([DivingAbility, FlyingAbility])
	static class JamesBondVehicle implements Vehicle {
		String getName() { "James Bond's vehicle" }
	}
	
	@Test
	void testMixin() {
		assert new Plane().fly() == "I'm the Concorde and I fly!"
		assert new Submarine().dive() == "I'm the Yellow Submarine and I dive!"
		
		assert new JamesBondVehicle().fly() == "I'm the James Bond's vehicle and I fly!"
		assert new JamesBondVehicle().dive() == "I'm the James Bond's vehicle and I dive!"
	}
}