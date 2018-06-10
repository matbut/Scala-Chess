package gameLogic

import structures._

class GameManager private(var turn:Color,val actionManager:ActionManager){

  def action(from:Position,to:Position){
    //action must be valid action
    actionManager.action(from, to)
    turn = turn.other
  }

  def isGameOver:Boolean = isWin || isDraw

  def isDraw:Boolean = actionManager.isStaleMate(turn) || actionManager.isImpossibilityOfCheckmate

  def isWin:Boolean = actionManager.isCheckMate(turn)

  def checkedKingPosition:Option[Position] = actionManager.checkedKingPosition(turn)

  def winner: Color = turn.other

  def isAction(from:Position,to:Position): Boolean =
    isTurn(from) && actionManager.isAction(from, to)

  def isCapture(from:Position,to:Position): Boolean =
    isTurn(from) && actionManager.isCapture(from, to)

  def isMove(from:Position,to:Position): Boolean =
    isTurn(from) && actionManager.isMove(from, to)

  def isOccupied(position: Position):Boolean =
    actionManager.isOccupied(position,turn)

  def isTurn(from:Position):Boolean =
    actionManager.board.piecesPlacement(from).color == turn

  override def toString = s"GameMenager\n\r Turn: $turn\n\r $ActionManager"
}
object GameManager{
  def apply(): GameManager = new GameManager(White,ActionManager())
  def apply(color: Color=White,board: Board): GameManager = new GameManager(color,ActionManager(board))
}