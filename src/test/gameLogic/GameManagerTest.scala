package gameLogic

import org.scalatest.FunSuite
import piece.{Bishop, King, Pawn, Piece}
import structures.{Black, Position, White}

class GameManagerTest extends FunSuite {

  test("KorchnoiKarpov1978DrawTest"){
    val boardKorchnoiKarpov1978:Map[Piece,Set[Position]] = Map(
        King(White) -> Set('F7),
        King(Black) -> Set('H7),
        Bishop(White) -> Set('G7),
        Pawn(White) -> Set('A3),
        Pawn(Black) -> Set('A4),
      )

    val gameMenager = GameManager(Black,Board(boardKorchnoiKarpov1978))
    assert(gameMenager.isDraw)
    assert(gameMenager.isGameOver)
    assert(!gameMenager.isWin)
  }

  test("VidmarMaroczy1932DrawTest"){
    val boardVidmarMaroczy1932:Map[Piece,Set[Position]] = Map(
      King(White) -> Set('G4),
      King(Black) -> Set('F7),
      Bishop(Black) -> Set('C7),
    )

    var gameMenager = GameManager(White,Board(boardVidmarMaroczy1932))
    assert(gameMenager.isDraw)
    assert(gameMenager.isGameOver)
    assert(!gameMenager.isWin)

    gameMenager = GameManager(Black,Board(boardVidmarMaroczy1932))
    assert(gameMenager.isDraw)
    assert(gameMenager.isGameOver)
    assert(!gameMenager.isWin)
  }
}
