package gameLogic

import structures._
import org.scalatest.FunSuite

class PieceActionsTest extends FunSuite {

  test("testCastle") {

  }

  test("testCastleShort") {

  }

  test("testCastleLong") {

  }

  test("testCaptureInPassing") {

  }

  test("testIsCapture") {

  }

  test("testIsMove") {
    assert(PieceActions.isMove(KNight(White),Position('B',1),Position('C',3)))
  }

}
