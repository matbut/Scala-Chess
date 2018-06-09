package piece

import structures._

object PieceActions {

  /*
  action:
    -> move
    -> capture
  */

  def isPossibleAction(piece: Piece, from:Position, to:Position):Boolean = {
    possibleActions(piece,from).contains(to)
  }

  def possibleActions(piece: Piece, from:Position):Set[Position] = {
    possibleMoves(piece,from) ++ possibleCaptures(piece,from)
  }

  def isPossibleMove(piece: Piece, from:Position, to:Position):Boolean = {
    possibleMoves(piece,from).contains(to)
  }

  def possibleMoves(piece: Piece, from:Position):Set[Position]={
    piece match{
      case _:King => kingMoves(from)
      case _:Queen => queenMoved(from)
      case _:Rook => rookMoves(from)
      case _:Bishop => bishopMoves(from)
      case _:KNight => kNightMoves(from)
      case Pawn(White) => WhitePawnMoves(from)
      case Pawn(Black) => BlackPawnMoves(from)
    }
  }

  def isPossibleCapture(piece: Piece, from:Position, to:Position):Boolean = {
    possibleCaptures(piece,from).contains(to)
  }

  def possibleCaptures(piece: Piece, from:Position):Set[Position]={
    piece match{
      case Pawn(White) => WhitePawnCaptures(from)
      case Pawn(Black) => BlackPawnCaptures(from)
      case _ => possibleMoves(piece,from)
    }
  }

  private def kingMoves(from: Position): Set[Position] = {
    val set:Set[Vect]=Set(Vect(0,1),Vect(1,1),Vect(1,0),Vect(-1,1), Vect(-1,0),Vect(0,-1),Vect(-1,-1),Vect(1,-1))
    set.map({case (vect:Vect) => from->vect}).filter((position:Position) => position.inside)
  }

  private def kNightMoves(from: Position): Set[Position] = {
    val set:Set[Vect]=Set(Vect(1,2),Vect(2,1),Vect(-1,2),Vect(-2,1), Vect(-1,-2),Vect(-2,-1),Vect(1,-2),Vect(2,-1))
    set.map({case (vect:Vect) => from->vect}).filter((position:Position) => position.inside)
  }

  private def WhitePawnMoves(from: Position): Set[Position] = {
    var set:Set[Vect] = Set(Vect(0,1))
    if (from.y==2) set += Vect(0,2)
    set.map({case (vect:Vect) => from->vect})
  }

  private def BlackPawnMoves(from: Position): Set[Position] = {
    var set:Set[Vect] = Set(Vect(0,-1))
    if (from.y==7) set += Vect(0,-2)
    set.map({case (vect:Vect) => from->vect})
  }

  private def rookMoves(from: Position): Set[Position] = Vect(0,1).line(from) ++ Vect(1,0).line(from)

  private def bishopMoves(from: Position): Set[Position] = Vect(1,1).line(from) ++ Vect(-1,1).line(from)

  private def queenMoved(from: Position): Set[Position] = rookMoves(from) ++ bishopMoves(from)

  private def WhitePawnCaptures(from: Position): Set[Position] = {
    var set:Set[Vect] = Set(Vect(1, 1),Vect(-1, 1))
    set.map({case (vect:Vect) => from->vect})
  }

  private def BlackPawnCaptures(from: Position): Set[Position] = {
    var set:Set[Vect] = Set(Vect(1, -1),Vect(-1, -1))
    set.map({case (vect:Vect) => from->vect})
  }
  /*

  def captureInPassing(): Unit ={

  }

  def castle: Unit ={ //Roszada

  }
  def castleShort(color: Color,from:Position, to:Position,board:Board): Unit ={ //Krótka roszada
    color match{
      case White =>
      case Black =>
    }
  }

  def castleLong: Unit ={ //Długa roszada

  }
  */

}
