package com.github.knives.scala

object PartialFunctions {
  def modNCurried(n: Int)(x: Int) = (x % n == 0)

  def modN(n: Int, x: Int) = (x % n == 0)
  
  def main(args: Array[String]) {
    // define curry function, see the underscore
    val mod2Curried = modNCurried(2) _
    
    // define partial function
    val mod2 = modN(2, _ : Int)
    
    println(Range(1,10).filter(mod2))
    println(Range(1,10).filter(mod2Curried))
    
    // Int => (Int => Boolean)
    println((modN _).curried)
    
    // Int => (Int => Boolean)
    println(modNCurried _)
  }
}