package gameLogic

import Structures._


object Appl extends App {
  val gameMenager = GameMenager
  println(gameMenager.board.toString)
  gameMenager.update(Position('B',1),Position('C',3))
  println(gameMenager.board.toString)
  gameMenager.update(Position('B',1),Position('C',3))
}