package gameLogic

import Structures._
import org.scalatest.FunSuite

class BoardTest extends FunSuite {

  val board = Board()

  test("testIsClear") {
    assert(board.isClear(Position('B',1),Position('C',3)))
    assert(!board.isClear(Position('B',1),Position('B',3)))
  }
  test("testIsOccupied"){
    assert(board.isOccupied(Position('B',1)))
  }
  test("testIsEmpty"){
    assert(board.isEmpty(Position('C',3)))
  }
}
