package gameLogic

import structures._

class ActionManager private(var board:Board){
  /*
  action
    -> move
    -> capture
  */



  def resetBoard(): Unit = {
    //TODO
    board = Board()
  }

  def action(from: Position,to:Position){
    if (isMove(from,to)) move(from,to)
    else if (isCapture(from,to)) capture(from,to)
  }

  def move(from: Position,to:Position){
    board.replace(from,to)
  }

  def capture(from:Position,to:Position){
    board.remove(to)
    move(from,to)
  }

  def isAction(from:Position,to:Position):Boolean =
    isCapture(from,to) || isMove(from,to)

  def isCapture(from:Position,to:Position):Boolean =
    board.isOccupied(from) && PieceActions.isCapture(board.piece(from),from,to) &&
      board.isOccupied(to) && (board.piece(from) other board.piece(to)) && board.isClear(from,to)

  def isMove(from:Position,to:Position):Boolean =
    board.isOccupied(from) && PieceActions.isMove(board.piece(from),from,to) &&
      board.isEmpty(to) && board.isClear(from,to)

  def isCheck(color:Color): Boolean ={
    val kingPosition = board.kingPosition(color)
    board.pieces(color.other).exists({case (position:Position,piece:Piece) => isCapture(position,kingPosition)})
  }

  def isMate(color:Color): Boolean ={
    board.pieces(color.other).forall({case (from:Position,piece:Piece) =>
      PieceActions.validMoves(piece,from).forall({case (to:Position) =>
        val prototype: ActionManager = ActionManager(this)
        prototype.move(from, to)
        prototype.isCheck(color)
      })
    })
  }

  def isCheckMate(color:Color): Boolean = isCheck(color) && isMate(color)
  def isStaleMate(color:Color): Boolean = !isCheck(color) && isMate(color)
  /*
  def isStaleMate(color:Color): Boolean ={
    board.pieces(color.other).forall({case (from:Position,piece:Piece) =>
      PieceActions.validMoves(piece,from).isEmpty
    }) && !isCheck(color)
  }
  */
  override def toString = s"Board: \n\r$board"
}
object ActionManager{

  def apply():ActionManager = new ActionManager(Board())

  def apply(prototype: ActionManager):ActionManager = new ActionManager(prototype.board)

}
