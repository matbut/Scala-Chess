package gameLogic

import Structures._
import main.gameLogic.Board

object GameMenager {
  val board=Board()
  var turn:Color=White

  def update(from:Position,to:Position){

    if(board.isEmpty(from))
      throw IllegalMoveException("No figure on position " ++ from.toString)

    if(board.color(from)!=turn)
      throw IllegalMoveException("Not your turn!")

    if(board.isEmpty(to)){
      move(from,to)
      return
    }

    if(board.color(to)==turn.other){
      capture(from,to)
      return
    }

    throw IllegalMoveException("Square occupied by your figure " ++ to.toString)
  }

  def move(from: Position,to:Position){
    board.changePosition(from,to)
    turn=turn.other
  }
  def capture(from:Position,to:Position){
    board.remove(to)
    move(from,to)
  }
}
