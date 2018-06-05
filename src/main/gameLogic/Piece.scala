package gameLogic

import Structures._

abstract sealed class Piece { //figura (bierka)
  val color:Color
  val symbol: Char
  var notMoved=true
  override def toString:String = if (color==White) symbol+"" else (symbol+32).toChar+""

  def other(piece: Piece):Boolean = piece.color!=color
}

case class King(override val color: Color) extends Piece{
  override val symbol = 'K'
}

case class Queen(override val color: Color) extends Piece{
  override val symbol = 'Q'
}

case class Rook(override val color: Color) extends Piece{
  override val symbol = 'R'
}

case class Bishop(override val color: Color)  extends Piece{
  override val symbol = 'B'
}

case class KNight(override val color: Color)  extends Piece{
  override val symbol = 'N'
}

case class Pawn(override val color: Color) extends Piece{
  override val symbol = 'P'
}