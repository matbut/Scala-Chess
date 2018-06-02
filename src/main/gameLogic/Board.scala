package main.gameLogic

import Structures._
import gameLogic._

import scala.collection.immutable.Stream.Empty
import scala.collection.mutable

class Board private(var figuresPosition: mutable.HashMap[Position, Figure]){

  //TODO is Position on board?

  def isEmpty(position: Position):Boolean = !isOccupied(position)

  def isOccupied(position: Position):Boolean =
    figuresPosition.contains(position)


  def figure(position: Position):Figure =
    figuresPosition(position)


  def color(position: Position):Color =
    figure(position).color


  def changePosition(oldPos: Position,newPos: Position):Boolean = {
    //TODO it better?
    val optionFigure = figuresPosition.remove(oldPos)
    if (optionFigure.nonEmpty){
      figuresPosition += ((newPos,optionFigure.get))
      true
    }else
      false
  }

  def remove(oldPos: Position):Boolean = figuresPosition.remove(oldPos).nonEmpty

  override def toString:String = {
    val newLine="\n\r"

    var stringBuilder=new StringBuilder
    stringBuilder ++= "  " ++ "ABCDEFGH" ++ "  " ++ newLine
    for(i <- 8 to 1 by -1){
      stringBuilder ++= i.toString ++ "|"
      for(j <- 1 to 8){
        val position=Position(j,i)
        if (isOccupied(position))
          stringBuilder ++= figure(position).toString
        else
          stringBuilder ++= "."
      }
      stringBuilder ++= "|" ++ i.toString ++ newLine
    }
    stringBuilder ++= "  " ++ "ABCDEFGH" ++ "  " ++ newLine
    stringBuilder.toString
  }
}

object Board{
  def apply():Board = {
    var figuresPosition:mutable.HashMap[Position,Figure]=mutable.HashMap[Position,Figure]()
    for (figure <- figures(White)++figures(Black))
      for (position <- positions(figure))
        figuresPosition+=((position,figure))
    new Board(figuresPosition)
  }

  private def figures(color:Color):Set[Figure] =
    Set[Figure](King(color),Queen(color),Rook(color),Bishop(color),KNight(color),Pawn(color))

  private def positions(figure: Figure):Set[Position] = {
    figure match{
      case King(White) => Set[Position](Position('E',1))
      case King(Black) => Set[Position](Position('E',8))
      case Queen(White) => Set[Position](Position('D',1))
      case Queen(Black) => Set[Position](Position('D',8))
      case Rook(White) => Set[Position](Position('A',1),Position('H',1))
      case Rook(Black) => Set[Position](Position('A',8),Position('H',8))
      case Bishop(White) => Set[Position](Position('C',1),Position('F',1))
      case Bishop(Black) => Set[Position](Position('C',8),Position('F',8))
      case KNight(White) => Set[Position](Position('B',1),Position('G',1))
      case KNight(Black) => Set[Position](Position('B',8),Position('G',8))
      case Pawn(White) => var set=Set[Position](); for (a <- 1 to 8) set+=Position(a,2); set
      case Pawn(Black) => var set=Set[Position](); for (a <- 1 to 8) set+=Position(a,7); set
    }
  }
}
