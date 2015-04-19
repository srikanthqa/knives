package com.github.knives.scala

object Tuples {
  def main(args : Array[String]) {
    // round bracket initialized tuple
    val t2 = (1, 2)
    val t3 = ("this", "is", "Tuple3")
    val t4 = (1,2,3,4)
    val t5 = (1,2,3,4,5)
    
    // up to 9 tuples
    
    println(t2.getClass)
    println(t3.getClass)
    println(t4.getClass)
    println(t5.getClass)
    
    println(t2._1 + t2._2)
  }
}