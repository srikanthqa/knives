package com.github.knives.scala.problem


class TicTacToe(val board: Array[Array[Char]] = Array.fill[Char](3, 3)(' ')) {
  /**
   * Option.empty = game is not end yet
   * Option[True] = has winner
   * Option[False] = draw
   */
  def state() : Option[Boolean] = {
    
    val downwardDiagonal = (0 until 3).map(i => board(i)(i))
    val upwardDiagonal = (0 until 3).map(i => board(2-i)(i))
    
    var test = downwardDiagonal.forall(ch => ch == 'X') ||
               downwardDiagonal.forall(ch => ch == 'O') ||
               upwardDiagonal.forall(ch => ch == 'X') ||
               upwardDiagonal.forall(ch => ch == 'O')
    
    for (i <- 0 until 3) {
      val row = (0 until 3).map(j => board(i)(j))
      val col = (0 until 3).map(j => board(j)(i))
      
      test = test ||
             row.forall(ch => ch == 'X') ||
             row.forall(ch => ch == 'O') ||
             col.forall(ch => ch == 'X') ||
             col.forall(ch => ch == 'O')
    }
    
    // have to add 'return' keyword, cuz it is not last statement
    if (test) return Option(test)
                       
    // if there is still slot, then the game is not end                   
    for (i <- 0 until 3; j <- 0 until 3) {
      if (board(i)(j) == ' ') Option.empty
    }
                       
    // the game is ended, but there is no winner
    // i.e. draw
    Option(false)
  }
  
  override def toString() = {
    val builder = new StringBuilder
    for (i <- 0 until 3) {
      for (j <- 0 until 3)
        builder.append(board(i)(j))
      builder.append('\n')
    }
    builder.toString()
  }
}

object TicTacToe {
  def main(args : Array[String]) {
    print(new TicTacToe)
    
    println("+++++++")
    
    val board = Array.fill[Char](3, 3)(' ');
    board(2)(2) = 'X'
    board(1)(1) = 'X'
    board(0)(0) = 'X'
    val winningBoard = new TicTacToe(board)
    print(winningBoard)
    println(winningBoard.state())
    
    board(0)(0) = 'O'
    println(winningBoard.state())

  }
}