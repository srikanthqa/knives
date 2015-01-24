package com.github.knives.scala.puzzle

package E3 {
  trait A {
    val audience: String
    println("Hello " + audience)
  }
  
  class BMember(a: String = "World") extends A {
    val audience = a
    println("I repeat: Hello " + audience)
  }
  
  class BConstructor(val audience: String = "World") extends A {
    println("I repeat: Hello " + audience)
  }
}
/**
 * When instantiating superclasses or 
 * traits, the parent constructor can be visualized as 
 * being executed before the first line of the child 
 * constructor but after the class definition.
 * 
 */
object LocationLocationLocation {
  def main(args: Array[String]) {
    new E3.BMember("Readers")
    new E3.BConstructor("Readers")
  }
}