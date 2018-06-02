package gameLogic

import Structures._

abstract sealed class Figure {
  val color:Color
  val symbol: Char
  override def toString:String = if (color==White) symbol+"" else (symbol+32).toChar+""
}

case class King(override val color: Color) extends Figure{
  override val symbol = 'K'
}

case class Queen(override val color: Color) extends Figure{
  override val symbol = 'Q'
}

case class Rook(override val color: Color) extends Figure{
  override val symbol = 'R'
}

case class Bishop(override val color: Color)  extends Figure{
  override val symbol = 'B'
}

case class KNight(override val color: Color)  extends Figure{
  override val symbol = 'N'
}

case class Pawn(override val color: Color) extends Figure{
  override val symbol = 'P'
}