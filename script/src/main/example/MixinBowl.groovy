/**
 * Category annotation can only apply on a single class unlike 'static method way'
 * However, you can still use @Category with 'use' keyword.
 * 
 * When use @Category, this refer to the enhance class
 * 
 * Furthermore, you can use @Mixin add @Category class method to the mixin class
 */

interface Vehicle {
	String getName()
}
 
@Category(Vehicle) 
class FlyingAbility {
	def fly() { "I'm the ${name} and I fly!" }
}

@Category(Vehicle) 
class DivingAbility {
	def dive() { "I'm the ${name} and I dive!" }
}

@Mixin(DivingAbility)
class Submarine implements Vehicle {
	String getName() { "Yellow Submarine" }
}

@Mixin(FlyingAbility)
class Plane implements Vehicle {
	String getName() { "Concorde" }
}

@Mixin([DivingAbility, FlyingAbility])
class JamesBondVehicle implements Vehicle {
	String getName() { "James Bond's vehicle" }
}

assert new Plane().fly() == "I'm the Concorde and I fly!"
assert new Submarine().dive() == "I'm the Yellow Submarine and I dive!"

assert new JamesBondVehicle().fly() == "I'm the James Bond's vehicle and I fly!"
assert new JamesBondVehicle().dive() == "I'm the James Bond's vehicle and I dive!"