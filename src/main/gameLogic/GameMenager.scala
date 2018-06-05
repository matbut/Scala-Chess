package gameLogic

import Structures._

object GameMenager {
  var turn:Color=White

  def action(from:Position,to:Position): Unit ={
    if(ActionMenager.isAction(from,to)){
      ActionMenager.action(from,to)
      turn=turn.other
    }
    else
      println("Bad move")
  }

  override def toString = s"GameMenager\n\r Turn: $turn\n\r $ActionMenager"
}
