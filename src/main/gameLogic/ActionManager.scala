package gameLogic

import piece._
import structures._

class ActionManager private(var board:Board){

  def action(from: Position,to:Position){
    board.replace(from,to)
  }

  def isAction(from:Position,to:Position):Boolean =
    isCapture(from,to) || isMove(from,to)

  def isCapture(from:Position,to:Position):Boolean =
    PieceActions.isPossibleCapture(board.piece(from),from,to) &&
      board.isOccupied(to) &&
      (board.piece(from) other board.piece(to)) &&
      compliesWithBasicRules(from,to)

  def isMove(from:Position,to:Position):Boolean =
    PieceActions.isPossibleMove(board.piece(from),from,to) &&
      board.isEmpty(to) &&
      compliesWithBasicRules(from,to)

  def compliesWithBasicRules(from:Position, to:Position):Boolean =
    board.isOccupied(from) &&
      board.isClear(from,to) &&
      !causesCheck(from,to)

  def causesCheck(from:Position,to:Position): Boolean ={
    val futureActionManager = copy
    futureActionManager.action(from:Position,to:Position)
    futureActionManager.isCheck(board.piece(from).color)
  }

  def moves(from:Position):Set[Position] = {
    PieceActions.possibleMoves(board.piece(from),from).filter((to:Position) => isMove(from,to))
  }

  def captures(from:Position):Set[Position] = {
    PieceActions.possibleMoves(board.piece(from),from).filter((to:Position) => isCapture(from,to))
  }

  def actions(from:Position):Set[Position] = {
    PieceActions.possibleMoves(board.piece(from),from).filter((to:Position) => isAction(from,to))
  }

  def actions(color:Color):Set[Position] = {
    board.pieces(color).flatMap({case (position:Position,piece:Piece)=>actions(position)})
  }

  def isCheck(color:Color): Boolean ={

    def isCapture(from:Position,to:Position):Boolean =
    PieceActions.isPossibleCapture(board.piece(from),from,to) &&
      board.isOccupied(to) &&
      (board.piece(from) other board.piece(to)) &&
      board.isOccupied(from) &&
      board.isClear(from,to)

    val kingPosition = board.kingPosition(color)
    board.pieces(color.other).exists({case (position:Position,piece:Piece) => isCapture(position,kingPosition)})
  }

  def isMate(color:Color): Boolean ={
    actions(color).isEmpty
  }

  def checkedKingPosition(color:Color):Option[Position] = {
    if(isCheck(color)) Some(board.kingPosition(color))
    else None
  }

  /*
  Impossibility of checkmate
  - king versus king
  - king and bishop versus king
  - king and knight versus king
  - king and bishop versus king and bishop with the bishops on the same colour.?
*/
  def isImpossibilityOfCheckmate:Boolean={
    Set[Set[Piece]](
      Set(King(White),King(Black)),
      Set(King(White),King(Black),Bishop(White)),
      Set(King(White),King(Black),Bishop(Black)),
      Set(King(White),King(Black),KNight(White)),
      Set(King(White),King(Black),KNight(Black)),
    ).
      filter((set) => set.size==board.pieces.size).
      contains(board.pieces.
        map({case (position,piece) => piece})
      )
  }

  def isCheckMate(color:Color): Boolean = {
    isCheck(color) && isMate(color)
  }
  def isStaleMate(color:Color): Boolean ={
    !isCheck(color) && isMate(color)
  }

  def isOccupied(position: Position,color: Color):  Boolean =
    board.isOccupied(position) && board.piece(position).color==color

  override def toString = s"Board: \n\r$board"

  def copy:ActionManager=new ActionManager(board.copy)
}
object ActionManager{

  def apply(board:Board):ActionManager = new ActionManager(board)

  def apply():ActionManager = new ActionManager(Board())

  def apply(prototype: ActionManager):ActionManager = prototype.copy

}
