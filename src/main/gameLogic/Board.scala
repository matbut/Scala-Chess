package gameLogic


import Structures._

import scala.collection.mutable

class Board private(var piecesPlacement: mutable.HashMap[Position, Piece]){

  var kingPosition:Map[Color,Position]=Map()
  piecesPlacement
    .filter({case (position:Position,piece:Piece) => piece.isInstanceOf[King]})
    .foreach({case (position:Position,piece:Piece) => kingPosition+=(piece.color -> position)})

  //TODO is Position on board?
  //Basic info

  def isEmpty(position: Position):Boolean = !isOccupied(position)

  def isOccupied(position: Position):Boolean = piecesPlacement.contains(position)

  def piece(position: Position):Piece = piecesPlacement(position)

  def color(position: Position):Color = piece(position).color

  //More advanced info

  def pieces(color: Color):Set[(Position,Piece)] = {
    piecesPlacement.filter({case((position:Position,piece:Piece)) => piece.color==color}).toSet
  }

  def isClear(from:Position,to:Position):Boolean = {
    Vect(from,to).contains.forall({case (position:Position) => !piecesPlacement.contains(position)})
  }

  //Operations on board

  def replace(oldPos: Position, newPos: Position){
    val piece = piecesPlacement.remove(oldPos).get
    piecesPlacement += ((newPos,piece))
    if(piece.isInstanceOf[King])
      kingPosition+=(piece.color -> newPos)
  }

  def remove(oldPos: Position): Unit ={
    piecesPlacement.remove(oldPos)
  }

  override def toString:String = {

    var stringBuilder=new StringBuilder
    stringBuilder ++= "  ABCDEFGH  \n\r"
    for(i <- 8 to 1 by -1){
      stringBuilder ++= s"$i|"
      for(j <- 1 to 8){
        val position=Position(j,i)
        if (isOccupied(position))
          stringBuilder ++= piece(position).toString
        else
          stringBuilder ++= "."
      }
      stringBuilder ++= s"|$i\n\r"
    }
    stringBuilder ++= "  ABCDEFGH  \n\r"
    stringBuilder.toString
  }
}

object Board{

  def apply():Board = {
    var piecesPlacement:mutable.HashMap[Position,Piece]=mutable.HashMap[Position,Piece]()
    for (piece <- pieces(White)++pieces(Black))
      for (position <- pieceStartPlacement(piece))
        piecesPlacement+=((position,piece))
    new Board(piecesPlacement)
  }

  def apply(prototype: Board):Board = new Board(prototype.piecesPlacement)

  private def pieces(color:Color):Set[Piece] =
    Set[Piece](King(color),Queen(color),Rook(color),Bishop(color),KNight(color),Pawn(color))

  private def pieceStartPlacement(figure: Piece):Set[Position] = {
    figure match{
      case King(White) => Set[Position]('E1)
      case King(Black) => Set[Position]('E8)
      case Queen(White) => Set[Position]('D1)
      case Queen(Black) => Set[Position]('D8)
      case Rook(White) => Set[Position]('A1,'H1)
      case Rook(Black) => Set[Position]('A8,'H8)
      case Bishop(White) => Set[Position]('C1,'F1)
      case Bishop(Black) => Set[Position]('C8,'F8)
      case KNight(White) => Set[Position]('B1,'G1)
      case KNight(Black) => Set[Position]('B8,'G8)
      case Pawn(White) => var set=Set[Position](); for (a <- 1 to 8) set+=Position(a,2); set
      case Pawn(Black) => var set=Set[Position](); for (a <- 1 to 8) set+=Position(a,7); set
    }
  }
}
