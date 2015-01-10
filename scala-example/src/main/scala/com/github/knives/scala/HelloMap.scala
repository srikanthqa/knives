package com.github.knives.scala

object HelloMap {
  def main(args: Array[String]) {
    val authorsToAge = Map("Raoul" -> 23, "Mario" -> 40, "Alan" -> 53)
    authorsToAge.foreach { case (name, age) => println(s"${name} is ${age} year old") }
  }
}