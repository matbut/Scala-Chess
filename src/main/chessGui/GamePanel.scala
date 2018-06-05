package chessGui

import java.awt.{Color, Point}

import structures.Position
import gameLogic.GameManager

import scala.swing.event.{MouseClicked, MouseMoved}
import scala.swing.{Dimension, Graphics2D, Panel}

class GamePanel extends Panel {
  preferredSize = new Dimension(900,900)
  //background = Color.WHITE

  def squareSize: Int = (preferredSize.height min preferredSize.width)/9
  var selectedPosition: Option[Position] = None
  var hoveredPosition: Option[Position] = None
  val painter = new ChessPainter(this)

  listenTo(mouse.clicks,mouse.moves)
  reactions += {
    case click: MouseClicked =>
      val clicked = squareAt(click.point)
      selectedPosition match {
        case None => selectedPosition = clicked; println("selected: "+clicked.get)
        case `clicked` => selectedPosition = None
        case Some(from) =>
          GameManager.action(from, clicked.get)
          println("Action from " + from + " to " + clicked.get)
          selectedPosition = None
          /*
          if(GameManager.gameOver) gameOver
           */
      }
      repaint
    case move: MouseMoved =>
      hoveredPosition = squareAt(move.point)
      repaint
  }

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)
    painter.paintComponent(g)
  }

  def squareAt(point: Point): Option[Position] = {
    val col = (point.x + squareSize/2) / squareSize //col <- {1..8}
    val row = (point.y +squareSize/2) / squareSize
    val position = Position(col,row)
    println(position)
    if (col < 0 || row < 0 || col >8 || row > 8) None else Some(position)
  }

  def gameOver(): Unit ={
    deafTo(mouse.moves,mouse.clicks)
  }
}
