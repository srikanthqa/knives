package com.github.knives.scala

object HelloWorld {
  def main(args: Array[String]) {
    var n : Int = 2
    while ( n <= 6 ) {
      println(s"Hello ${n} bottles of beer")
      n += 1
    }
    
    2 to 6 foreach { n => println(s"Hello ${n} bottles of cups") }
  }
}