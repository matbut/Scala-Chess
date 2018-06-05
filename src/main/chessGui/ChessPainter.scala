package chessGui

import java.awt.{BasicStroke, Color}
import structures.{Position, White}
import scala.swing.{Font, Graphics2D, Rectangle}
import gameLogic.{ActionManager, GameManager}

class ChessPainter(panel:GamePanel) {
  val squareSize: Int = panel.squareSize
  val selectedPosition: Option[Position] = panel.selectedPosition

  val backgroundLight = new Color(255, 218, 179)
  val backgroundDark = new Color(198, 140, 83)
  val pieceWhite: Color = Color.WHITE
  val pieceBlack = new Color(128, 66, 0)
  val selected = new Color(153, 204, 255)
  val hovered = new Color(111, 220, 111)

  def paintComponent(g: Graphics2D): Unit ={
    drawSquares()
    drawPieces()
    drawSelectedSquare()
    drawHoveredSquare()
    drawCoordinates

    def drawSquares(): Unit ={
      val oldPaint = g.getPaint
      val offset = squareSize/2
      for(col <- 0 to 7; row <-0 to 7){
        g.setPaint(if((col+row)%2==0) backgroundDark else backgroundLight)
        g.fillRect(col*squareSize+offset, row*squareSize+offset, squareSize, squareSize)
      }
      g.setPaint(oldPaint)
    }
    def drawPieces(): Unit ={
      val oldPaint = g.getPaint
      g.setFont(new Font(g.getFont.getName, 0, 3*squareSize/5))
      val squareOffset = squareSize/2
      for((position,piece) <- ActionManager.board.piecesPlacement.iterator){
        g.setPaint(if(piece.color == White) pieceWhite else pieceBlack)
        val rect = g.getFont.getStringBounds(piece.uniSym.toString,g.getFontRenderContext)
        val yOffset = (rect.getY+rect.getHeight/2).toInt
        val xOffset = (rect.getX+rect.getWidth/2).toInt
        g.drawString(piece.uniSym.toString,position.x*squareSize-xOffset, position.y*squareSize-yOffset)
      }
      g.setPaint(oldPaint)
    }
    def drawSelectedSquare(): Unit ={
      val oldStroke = g.getStroke
      val oldPaint = g.getPaint
      g.setStroke(new BasicStroke(3f))
      panel.selectedPosition match {
        case None =>
        case Some(position) =>
          g.setPaint(selected)
          val offset = squareSize/2
          g.draw(new Rectangle((position.x-1)*squareSize+offset, (position.y-1)*squareSize+offset,squareSize,squareSize))
      }
      g.setStroke(oldStroke)
      g.setPaint(oldPaint)
    }
    def drawHoveredSquare(): Unit ={
      val oldStroke = g.getStroke
      val oldPaint = g.getPaint
      g.setStroke(new BasicStroke(3f))
      panel.hoveredPosition match {
        case None =>
        case Some(hoveredPos) =>
          panel.selectedPosition match {
            case None =>
            case Some(selectedPos) =>
              if (GameManager.isAction(selectedPos, hoveredPos)) {
                g.setPaint(hovered)
                println("hovered")
                val offset = squareSize/2
                g.draw(new Rectangle((hoveredPos.x-1)*squareSize+offset, (hoveredPos.y-1)*squareSize+offset,squareSize,squareSize))
              }
          }
      }
      g.setStroke(oldStroke)
      g.setPaint(oldPaint)
    }
    def drawCoordinates(): Unit ={
      val oldPaint = g.getPaint
      g.setFont(new Font(g.getFont.getName,g.getFont.getStyle,squareSize*2/5))
      g.setPaint(Color.GRAY)
      val rect = g.getFont.getStringBounds("0",g.getFontRenderContext)
      val yOffset = (rect.getY+rect.getHeight/2).toInt
      val xOffset = (rect.getX+rect.getWidth/2).toInt
      for (row <-1 to 8; col = ('A'-1+row).toChar){
        g.drawString(row.toString,squareSize/4-xOffset,row*squareSize-yOffset)
        g.drawString(row.toString,squareSize*35/4-xOffset,row*squareSize-yOffset)
        g.drawString(col.toString,row*squareSize-xOffset,squareSize/4-yOffset)
        g.drawString(col.toString,row*squareSize-xOffset,squareSize*35/4-yOffset)
      }
      g.setPaint(oldPaint)
    }
  }
}
