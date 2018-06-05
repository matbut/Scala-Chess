package gameLogic

import Structures._

object ActionMenager {
  /*
  action
    -> move
    -> capture
  */

  val board=Board()
  //var oldBoard:Board

  def action(from: Position,to:Position){
    if (isMove(from,to)) move(from,to)
    else if (isCapture(from,to)) capture(from,to)
  }

  def move(from: Position,to:Position){
    board.piece(from).notMoved=false
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
    board.pieces(color.other).forall({case (position:Position,piece:Piece) => isCapture(position,kingPosition)})
  }
  /*
  def isPinned(position: Position):Boolean ={ //związanie
    false
  }
  */
  override def toString = s"Board: \n\r$board"
}
