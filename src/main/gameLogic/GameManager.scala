package gameLogic

import structures._

object GameManager {
  var turn:Color=White
  var gameOver: Boolean = false
  var winner: Option[Color] = None

  def action(from:Position,to:Position): Unit ={
    if(isAction(from, to)) {
      ActionManager.action(from, to)
      turn = turn.other
      println(turn)
    }
  }

  def isAction(from:Position,to:Position): Boolean = {
    if(!ActionManager.isAction(from, to)) println("Bad move")
    else if(ActionManager.board.piecesPlacement(from).color != turn) println("not your turn")
    ActionManager.isAction(from, to) && ActionManager.board.piecesPlacement(from).color == turn
  }

  def resetGame(): Unit = {
    ActionManager.resetBoard()
    turn = White
    gameOver = false
    winner = None
  }

  override def toString = s"GameMenager\n\r Turn: $turn\n\r $ActionManager"
}
