package piece

import structures.{Black, Position, White}

object PieceStartPlacement {

  def basic:Map[Piece,Set[Position]] = Map(
    King(White) -> Set('E1),
    King(Black) -> Set('E8),
    Queen(White) -> Set('D1),
    Queen(Black) -> Set('D8),
    Rook(White) -> Set('A1,'H1),
    Rook(Black) -> Set('A8,'H8),
    Bishop(White) -> Set('C1,'F1),
    Bishop(Black) -> Set('C8,'F8),
    KNight(White) -> Set('B1,'G1),
    KNight(Black) -> Set('B8,'G8),
    Pawn(White) -> (1 to 8).map((cord:Int) => Position(cord,2)).toSet,
    Pawn(Black) -> (1 to 8).map((cord:Int) => Position(cord,7)).toSet,
  )
}
