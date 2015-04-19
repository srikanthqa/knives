package com.github.knives.scala

object ForLoop {
  def main(args : Array[String]) {
    for (i <- 0 to 9) {
      println(i)
    }
    
    for (i <- Range(0, 9)) {
      println(i)
    }
    
    for (i <- List("list", "is", "fun")) {
      println(i)
    }
    
    // map
    for ((k , v) <- Map("x" -> 24, "y" -> 25, "z" -> 26)) {
      println(k + " --> " + v)
    }
    
    // nested loop
    for (i <- 0 to 9; j <- i to 9) {
      println(s"$i $j")
    }
  }
}