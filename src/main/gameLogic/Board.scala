package gameLogic


import piece.{King, Piece, PieceStartPlacement}
import structures._

import scala.collection.mutable

class Board private(var piecesPlacement: mutable.HashMap[Position, Piece]){

  var kingPosition:Map[Color,Position]=Map()
  piecesPlacement
    .filter({case (position:Position,piece:Piece) => piece.isInstanceOf[King]})
    .foreach({case (position:Position,piece:Piece) => kingPosition+=(piece.color -> position)})

  //Basic board info

  def isEmpty(position: Position):Boolean = !isOccupied(position)

  def isOccupied(position: Position):Boolean = piecesPlacement.contains(position)

  def piece(position: Position):Piece = piecesPlacement(position)

  def color(position: Position):Color = piece(position).color

  //More advanced info
  def pieces:Set[(Position,Piece)] = {
    piecesPlacement.toSet
  }

  def pieces(color: Color):Set[(Position,Piece)] = {
    pieces.filter({case((position:Position,piece:Piece)) => piece.color==color}).toSet
  }

  def isClear(from:Position,to:Position):Boolean = {
    Vect(from,to).contains.forall((position:Position) => !piecesPlacement.contains(position))
  }

  //Board operations

  def replace(oldPos: Position, newPos: Position){
    if(isOccupied(newPos))
      remove(newPos)
    val piece = piecesPlacement.remove(oldPos).get
    piece.notMoved=false
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

  def copy:Board=new Board(piecesPlacement.clone)
}

object Board{
  def apply(placement: Map[Piece,Set[Position]]=PieceStartPlacement.basic):Board = {

    var piecesPlacement:mutable.HashMap[Position,Piece]=new mutable.HashMap[Position,Piece]()

    placement.flatMap({
      case (piece:Piece,set:Set[Position]) => set.map({
        case (position:Position) => position -> piece})})
          .foreach({case (position:Position,piece:Piece) => piecesPlacement+=((position,piece))})

    new Board(piecesPlacement)
  }

  def apply(prototype: Board):Board = prototype.copy
}
