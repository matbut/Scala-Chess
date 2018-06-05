package gameLogic

import structures._

object PieceActions {

  def isMove(figure: Piece, from:Position, to:Position):Boolean = {
    move(figure,from).contains(to)
    /*val move = Vect(from,to)
    figure match{
      case _:King => (move rot90 Vect(0,1)) || (move rot90 Vect(1,1))
      case _:Queen => move.isVerticall || move.isHorizontall || move.isDiagonall
      case _:Rook => move.isVerticall || move.isHorizontall
      case _:Bishop => move.isDiagonall
      case _:KNight => (move rot90 Vect(2,1)) || (move rot90 Vect(1,2))
      case Pawn(White) => move =~= Vect(0,1) || (move =~= Vect(0,2) && move.from.y==2)
      case Pawn(Black) => move =~= Vect(0,-1) || (move =~= Vect(0,-2) && move.from.y==7)
    }*/
  }

  def move(figure: Piece,from:Position):Stream[Position]={
    figure match{
      case _:King => kingMoves(from)
      case _:Queen => queenMoved(from)
      case _:Rook => rookMoves(from)
      case _:Bishop => bishopMoves(from)
      case _:KNight => kNightMoves(from)
      case Pawn(White) => WhitePawnMoves(from)
      case Pawn(Black) => BlackPawnMoves(from)
    }
  }

  def kingMoves(from: Position): Stream[Position] = {
    val set:Set[Vect]=Set(Vect(0,1),Vect(1,1),Vect(1,0),Vect(-1,1), Vect(-1,0),Vect(0,-1),Vect(-1,-1),Vect(-1,1))
    set.map({case (vect:Vect) => from->vect}).toStream
  }

  def kNightMoves(from: Position): Stream[Position] = {
    val set:Set[Vect]=Set(Vect(1,2),Vect(2,1),Vect(-1,2),Vect(-2,1), Vect(-1,-2),Vect(-2,-1),Vect(1,-2),Vect(2,-1))
    set.map({case (vect:Vect) => from->vect}).toStream
  }

  def WhitePawnMoves(from: Position): Stream[Position] = {
    var set:Set[Vect] = Set(Vect(0,1))
    if (from.y==2) set += Vect(0,2)
    set.map({case (vect:Vect) => from->vect}).toStream
  }

  def BlackPawnMoves(from: Position): Stream[Position] = {
    var set:Set[Vect] = Set(Vect(0,-1))
    if (from.y==7) set += Vect(0,-2)
    set.map({case (vect:Vect) => from->vect}).toStream
  }

  def rookMoves(from: Position): Stream[Position] = Vect(0,1).line(from) ++ Vect(1,0).line(from)

  def bishopMoves(from: Position): Stream[Position] = Vect(1,1).line(from) ++ Vect(-1,1).line(from)

  def queenMoved(from: Position): Stream[Position] = rookMoves(from) ++ bishopMoves(from)



  def isCapture(figure: Piece, from:Position, to:Position):Boolean = {
    val move = Vect(from, to).free
    figure match {
      case Pawn(White) => move == Vect(1, 1) || move == Vect(-1, 1)
      case Pawn(Black) => move == Vect(1, -1) || move == Vect(-1, -1)
      case _ => isMove(figure, from, to)
    }
  }
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


}
