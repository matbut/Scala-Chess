package gameLogic

import Structures._

object FigureMoves {
  def isMove(figure: Figure,from:Position,to:Position):Boolean = {
    val move = Vect(from,to)
    figure match{
      case _:King => (move rot90 Vect(0,1)) || (move rot90 Vect(1,1))
      case _:Queen => (move H Vect(0,1)) ||  (move H Vect(1,1))
      case _:Rook => move H Vect(0,1)
      case _:Bishop => move H Vect(1,1)
      case _:KNight => (move rot90 Vect(2,1)) || (move rot90 Vect(1,2))
      case Pawn(White) => move =~= Vect(0,1) || (move =~= Vect(0,2) && move.from.y==2)
      case Pawn(Black) => move =~= Vect(0,-1) || (move =~= Vect(0,-2) && move.from.y==7)
    }
  }
  def isCapture(figure: Figure,from:Position,to:Position):Boolean = {
    val move = Vect(from, to)
    figure match {
      case _: Pawn => move == Vect(1, 1) || move == Vect(-1, 1)
      case _ => isMove(figure, from, to)
    }
  }
}
