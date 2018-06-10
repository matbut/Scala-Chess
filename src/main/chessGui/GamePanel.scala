package chessGui

import java.awt.Point

import structures.Position
import gameLogic.GameManager

import scala.swing.Dialog.Message
import scala.swing.event.{MouseMoved, MousePressed}
import scala.swing.{Dialog, Dimension, Graphics2D, Panel}

class GamePanel extends Panel {
  preferredSize = new Dimension(900,900)
  val squareSize: Int = (preferredSize.height min preferredSize.width)/9

  var gameManager = GameManager()

  var selectedPosition: Option[Position] = None
  var hoveredPosition: Option[Position] = None
  var painter = new ChessPainter(squareSize,gameManager)

  listenTo(mouse.clicks,mouse.moves)
  reactions += {
    case click: MousePressed =>
      val clicked = squareAt(click.point)
      selectedPosition match {
        case None => if(clicked.nonEmpty && gameManager.isOccupied(clicked.get)) selectedPosition = clicked
          //println("selected: "+clicked.get)
        case `clicked` => selectedPosition = None
        case Some(from) =>
          if(gameManager.isAction(from, clicked.get))
            gameManager.action(from, clicked.get)
          //println("Action from " + from + " to " + clicked.get)
          selectedPosition = None

          if(gameManager.isGameOver){
            repaint
            gameOver()
          }
      }
      repaint
    case move: MouseMoved =>
      hoveredPosition = squareAt(move.point)
      repaint
  }

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)
    painter.paintComponent(g,selectedPosition,hoveredPosition)
  }

  def squareAt(point: Point): Option[Position] = {
    val col = (point.x + squareSize/2) / squareSize //col <- {1..8}
    val row = 9 - (point.y + squareSize/2) / squareSize
    val position = Position(col,row)
    if (position.inside) Some(position) else None
  }

  def gameOver(): Unit ={
    deafTo(mouse.moves,mouse.clicks)
    var message="Game over!\n"

    if (gameManager.isWin)
      message++="Winner: "++gameManager.winner.playerName
    else
      message++="No winner"

    Dialog.showMessage(this,message,"Chess",messageType = Message.Plain)
  }

  def reset(): Unit ={
    gameManager = GameManager()
    painter = new ChessPainter(squareSize,gameManager)
    listenTo(mouse.clicks,mouse.moves)
    selectedPosition = None
    hoveredPosition = None
    repaint
  }
}
