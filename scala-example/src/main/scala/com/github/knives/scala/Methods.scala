package com.github.knives.scala

object Methods {
  def multiplyByTwo(x:Int):Int = {
    println("Inside multiplyByTwo")
    x * 2
  }
  
  def main(args: Array[String]) {
    val r = multiplyByTwo(5)
    println(r)
  }
}