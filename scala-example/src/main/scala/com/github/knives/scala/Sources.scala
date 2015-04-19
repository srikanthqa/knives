package com.github.knives.scala

import scala.io.Source
import java.nio.file.Paths

object Sources {
  def main(args : Array[String]) {
    // calling System.out.println instead of println
    System.out.println(Source.fromString("Hello world").map( x => x.toInt ).fold(0)(_ + _))
    
    System.out.println(Source.fromFile(Paths.get("/tmp/test").toFile())
        .getLines().fold("")((x1, x2) => x1 + "line: " + x2 + "\n"))
        
    // in Scala you still use Source to wrap all io inputstream etc... in Java
  }
}