package com.github.knives.scala.puzzle

object HiThere {
  def main(args: Array[String]) {
    List(1, 2).map { i => i + 1 }
    List(1, 2).map { i => println("Hi"); i + 1 }
    List(1, 2).map { _ + 1 }
    List(1, 2).map { println("Hi"); _ + 1 }
  }
}