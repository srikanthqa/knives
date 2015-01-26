package com.github.knives.scala.puzzle

object ArgArrgh {
  def main(args: Array[String]) {
    def square[T : Numeric](n: T) = implicitly[Numeric[T]].times(n, n)
    
    def twiceA[T](f: T => T, a: T) = f(f(a))
    def twiceB[T](f: T => T)(a: T) = f(f(a))
    def twiceC[T](a: T, f: T => T) = f(f(a))
    def twiceD[T](a: T)(f: T => T) = f(f(a))
    
    /**
     * For square to be applicable as an argument, the compiler 
     * has to know that T is bound to (a type that can implicitly 
     * be converted to) a Numeric. Even though it would appear that 2, 
     * as an Int, obviously fulfills this condition, that information is not 
     * available to the compiler in a multi-arg list. Only in the fourth version 
     * is T bound "sufficiently early" for square to be allowed.
     */
    //twiceA(square, 2)
    //twiceB(square)(2)
    //twiceC(2, square)
    println(twiceD(2)(square))
  }
}