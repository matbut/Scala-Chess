package chessGui

import java.awt.{BasicStroke, Color}

import structures.{Position, White}

import scala.swing.{Dimension, Font, Graphics2D, Rectangle}
import gameLogic.{ActionManager, GameManager}

class ChessPainter(val squareSize: Int,var gameManager: GameManager) {
  val backgroundLight = new Color(255, 218, 179)
  val backgroundDark = new Color(198, 140, 83)
  val pieceWhite: Color = Color.WHITE
  val pieceBlack = new Color(128, 66, 0)
  val selected = new Color(153, 204, 255)
  val moveColor = new Color(111, 220, 111)
  val captureColor = new Color(255, 187, 51)
  val checkColor:Color = Color.RED

  def paintComponent(g: Graphics2D,selectedPosition:Option[Position],hoveredPosition:Option[Position]): Unit ={
    drawSquares()
    drawPieces()
    drawCheckedKingSquare()
    drawSelectedSquare()
    drawHoveredSquare()
    drawTurnLine()
    drawCoordinates()


    def drawSquares(): Unit ={
      val oldPaint = g.getPaint
      val offset = squareSize/2
      for(col <- 0 to 7; row <-0 to 7){
        g.setPaint(if((col+row)%2!=0) backgroundDark else backgroundLight)
        g.fillRect(col*squareSize+offset, row*squareSize+offset, squareSize, squareSize)
      }
      g.setPaint(oldPaint)
    }
    def drawPieces(): Unit ={
      val oldPaint = g.getPaint
      g.setFont(new Font(g.getFont.getName, 0, 3*squareSize/5))
      val squareOffset = squareSize/2
      //TODO
      for((position,piece) <- gameManager.actionManager.board.piecesPlacement.iterator){
        g.setPaint(if(piece.color == White) pieceWhite else pieceBlack)
        val rect = g.getFont.getStringBounds(piece.uniSym.toString,g.getFontRenderContext)
        val yOffset = (rect.getY+rect.getHeight/2).toInt
        val xOffset = (rect.getX+rect.getWidth/2).toInt
        g.drawString(piece.uniSym.toString,position.x*squareSize-xOffset, (9-position.y)*squareSize-yOffset)
      }
      g.setPaint(oldPaint)
    }
    def drawSelectedSquare(): Unit ={
      val oldStroke = g.getStroke
      val oldPaint = g.getPaint
      g.setStroke(new BasicStroke(3f))
      selectedPosition match {
        case None =>
        case Some(position) =>
          g.setPaint(selected)
          val offset = squareSize/2
          g.draw(new Rectangle((position.x-1)*squareSize+offset, (8-position.y)*squareSize+offset,squareSize,squareSize))
      }
      g.setStroke(oldStroke)
      g.setPaint(oldPaint)
    }
    def drawHoveredSquare(): Unit ={
      val oldStroke = g.getStroke
      val oldPaint = g.getPaint
      g.setStroke(new BasicStroke(3f))
      hoveredPosition match {
        case None =>
        case Some(hoveredPos) =>
          selectedPosition match {
            case None =>
            case Some(selectedPos) =>
              if (gameManager.isAction(selectedPos, hoveredPos)) {
                if(gameManager.isCapture(selectedPos,hoveredPos))
                  g.setPaint(captureColor)
                else //isMove
                  g.setPaint(moveColor)
                val offset = squareSize/2
                g.draw(new Rectangle((hoveredPos.x-1)*squareSize+offset, (8-hoveredPos.y)*squareSize+offset,squareSize,squareSize))
              }
          }
      }
      g.setStroke(oldStroke)
      g.setPaint(oldPaint)
    }
    def drawCheckedKingSquare(): Unit ={
      val oldStroke = g.getStroke
      val oldPaint = g.getPaint
      g.setStroke(new BasicStroke(3f))
      gameManager.checkedKingPosition match {
        case None =>
        case Some(position) =>
          g.setPaint(checkColor)
          val offset = squareSize/2
          g.draw(new Rectangle((position.x-1)*squareSize+offset, (8-position.y)*squareSize+offset,squareSize,squareSize))
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
        g.drawString((9-row).toString,squareSize/4-xOffset,row*squareSize-yOffset)
        g.drawString((9-row).toString,squareSize*35/4-xOffset,row*squareSize-yOffset)
        g.drawString(col.toString,row*squareSize-xOffset,squareSize/4-yOffset)
        g.drawString(col.toString,row*squareSize-xOffset,squareSize*35/4-yOffset)
      }
      g.setPaint(oldPaint)
    }
    def drawTurnLine():Unit ={
      val oldPaint = g.getPaint
      val oldStroke = g.getStroke
      g.setPaint(moveColor)
      g.setStroke(new BasicStroke(3f))
      val startX = squareSize/2
      val length = squareSize * 8
      val startY = if(gameManager.turn == White) squareSize*17/2 else squareSize/2
      g.drawLine(startX,startY,startX+length,startY)
      g.setPaint(oldPaint)
      g.setStroke(oldStroke)
    }
  }
}
