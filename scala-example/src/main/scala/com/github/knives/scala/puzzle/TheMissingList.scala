package com.github.knives.scala.puzzle

/**
 * Even though collections.map would appear to map an iterable
 * to another "nice" iterable, since the Collections Redesign 
 * the type of the returned iterable will (usually) match the 
 * input type. Which, for sets, means...no duplicates. And yes, 
 * foldLeft would obviously be a much nicer way to do this 
 */
object TheMissingList {
  def main(args: Array[String]) {
    def sumSizes(collections: Iterable[TraversableOnce[_]]): Int = collections.map(_.size).sum
    
    println(sumSizes(List(Set(1, 2), List(3, 4))))
    println(sumSizes(Set(List(1, 2), Set(3, 4))))
  }
}