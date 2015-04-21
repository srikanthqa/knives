package com.github.knives.scala

object MatchExpressions {
  def matchColor(color : String) : String = {
    color match {
      case "red" => "RED"
      case "blue" => "BLUE"
      case "green" => "GREEN"
      case _ => "UNKNOWN COLOR: " + color
    }
  }
  
  def main(args : Array[String]) {
    println(matchColor("white"))
    println(matchColor("blue"))
  }
}