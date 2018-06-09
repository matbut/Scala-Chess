package piece

import structures._

abstract sealed class Piece { //figura (bierka)
  //properties
  val color:Color
  val points:Int
  var notMoved=true

  //Print
  val symbol: Char
  val unicode: Int

  def uniSym: Char = unicode.toChar// - 0x0006).toChar

  override def toString:String = if (color==White) symbol+"" else (symbol+32).toChar+""

  def other(piece: Piece):Boolean = piece.color!=color
}

case class King(override val color: Color) extends Piece{
  override val symbol = 'K'
  override val unicode = 0x265A
  override val points = 200
}

case class Queen(override val color: Color) extends Piece{
  override val symbol = 'Q'
  override val unicode = 0x265B
  override val points = 9
  val weight:Weight = Heavy
}

case class Rook(override val color: Color) extends Piece{
  override val symbol = 'R'
  override val unicode = 0x265C
  override val points = 5
  val weight:Weight = Heavy
}

case class Bishop(override val color: Color)  extends Piece{
  override val symbol = 'B'
  override val unicode = 0x265D
  override val points = 3
  val weight:Weight = Light
}

case class KNight(override val color: Color)  extends Piece{
  override val symbol = 'N'
  override val unicode = 0x265E
  override val points = 3
  val weight:Weight = Light
}

case class Pawn(override val color: Color) extends Piece{
  override val symbol = 'P'
  override val unicode = 0x265F
  override val points = 1
}

