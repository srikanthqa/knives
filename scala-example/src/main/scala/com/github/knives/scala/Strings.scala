package com.github.knives.scala

object Strings {
  def main(args: Array[String]) {
    var x1:String = "Sally"
    var x2:String = "Sally"
    if (x1.equals(x2)) {
      println("x1 " + x1 + " and x2 " +  x2 + " are equal")
    } else {
      println("x1 " + x1 + " and x2 " +  x2 + " are not equal")
    }
    
    x2 = "Sam"
    if (x1.equals(x2)) {
      println("x1 " + x1 + " and x2 " +  x2 + " are equal")
    } else {
      println("x1 " + x1 + " and x2 " +  x2 + " are not equal")
    }
    
    val x3 = x2.toUpperCase
    if (x3.contentEquals(x2)) {
      println("x3 " + x3 + " and x2 " +  x2 + " are equal")
    } else {
      println("x3 " + x3 + " and x2 " +  x2 + " are not equal")
    }
  }
}