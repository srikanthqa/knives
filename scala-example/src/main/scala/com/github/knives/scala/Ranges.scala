package com.github.knives.scala

object Ranges {
  def main(args: Array[String]) {
    // companion object
    val x = Range(1, 10)
    
    // actual class
    val y = new Range(0, 10, 1)
    
    println(x.step)
    // multplication 1*2*3*...*9
    println(x.fold(1)(_ * _))
    
    // power: by map to 2, since you have an stream of 2,...2 (n element) 
    // fold with multiplication op, it becomes power
    println(y.map(_ => 2).fold(1)(_ * _))
  }
}