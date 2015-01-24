package com.github.knives.scala.puzzle

package E4 {
  trait A {
    val foo: Int
    val bar = 10
    println("In A: foo: " + foo + ", bar: " + bar)
  }
  
  class B extends A {
    val foo: Int = 25
    println("In B: foo: " + foo + ", bar: " + bar)
  }
  
  class C extends B {
    override val bar = 99
    println("In C: foo: " + foo + ", bar: " + bar)
  }
}

/**
 * Notice that bar is a val that is overridden in C. 
 * The Scala compiler will only initialize vals once, 
 * so since bar will be initialized in C it is not 
 * initialized before that time and appears as its 
 * default value (0, in this case) during the construction 
 * of the superclass. 
 */
object NowYouSeeMe {
  def main(args: Array[String]) {
    new E4.C
  }
}