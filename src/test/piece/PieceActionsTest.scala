package piece

import org.scalatest.FunSuite
import structures._

class PieceActionsTest extends FunSuite {

  test("testIsKNightMove") {
    assert(PieceActions.isPossibleMove(KNight(White),Position('B',1),Position('C',3)))
  }

}
