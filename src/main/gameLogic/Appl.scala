package gameLogic

import Structures._


object Appl extends App {

  println(GameMenager.toString)
  GameMenager.action('B1,'C3)
  println(GameMenager.toString)

  println(PieceActions.rookMoves(100))
}