package gameLogic

import structures._

object GameManager {
  var turn:Color=White
  var gameOver: Boolean = false
  var winner: Option[Color] = None
  var actionManager:ActionManager=ActionManager()

  def action(from:Position,to:Position){
    if(isAction(from, to)) {
      actionManager.action(from, to)
      turn = turn.other
      println(turn)
    }
    gameOver=actionManager.isCheckMate(turn)
    if(gameOver)
      println("Game Over")
  }

  def isAction(from:Position,to:Position): Boolean = {
    if(!actionManager.isAction(from, to)) println("Bad move")
    else if(actionManager.board.piecesPlacement(from).color != turn) println("not your turn")
    actionManager.isAction(from, to) && actionManager.board.piecesPlacement(from).color == turn
  }

  def resetGame(): Unit = {
    actionManager.resetBoard()
    turn = White
    gameOver = false
    winner = None
  }



  override def toString = s"GameMenager\n\r Turn: $turn\n\r $ActionManager"
}
