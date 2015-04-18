package com.github.knives.scala

import com.github.knives.scala.zoo.Cat
import com.github.knives.scala.zoo.Dog

class Giraffe
class Bear
class Hippo

object Animals {
  def main(args: Array[String]) {
    // note: how you don't have () for constructor
    // this is because default constructor don't have any parameter
    // same goes with method with zero parameter
    
    val g1 = new Giraffe
    val g2 = new Giraffe
    val b = new Bear
    val h = new Hippo
    println(g1)
    println(g2)
    println(b)
    println(h)
    
    new Cat
    
    println((new Dog).bark)
  }
}