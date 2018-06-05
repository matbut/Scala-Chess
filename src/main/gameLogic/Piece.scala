package gameLogic

import structures._

abstract sealed class Piece { //figura (bierka)
  val color:Color
  val symbol: Char
  val unicode: Int
  var notMoved=true
  def uniSym: Char = if(color == White) unicode.toChar else (unicode.toChar)// - 0x0006).toChar
  override def toString:String = if (color==White) symbol+"" else (symbol+32).toChar+""

  def other(piece: Piece):Boolean = piece.color!=color
}

case class King(override val color: Color) extends Piece{
  override val symbol = 'K'
  override val unicode = 0x265A
}

case class Queen(override val color: Color) extends Piece{
  override val symbol = 'Q'
  override val unicode = 0x265B
}

case class Rook(override val color: Color) extends Piece{
  override val symbol = 'R'
  override val unicode = 0x265C
}

case class Bishop(override val color: Color)  extends Piece{
  override val symbol = 'B'
  override val unicode = 0x265D
}

case class KNight(override val color: Color)  extends Piece{
  override val symbol = 'N'
  override val unicode = 0x265E
}

case class Pawn(override val color: Color) extends Piece{
  override val symbol = 'P'
  override val unicode = 0x265F
}