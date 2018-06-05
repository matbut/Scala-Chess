package chessGui

import gameLogic.GameManager
import structures.{Black, Color, White}

import scala.swing.Dialog.{Message, showInput}
import scala.swing.event.WindowClosing
import scala.swing.{Action, Button, MainFrame, MenuBar, SimpleSwingApplication, Swing}

object Appl extends GuiApp
class GuiApp extends SimpleSwingApplication{
  def top: MainFrame = new MainFrame {
    val panel = new GamePanel

    title = "Chess"
    menuBar = new MenuBar{
      contents += new Button(Action("New Game") {
        GameManager.resetGame(); panel.repaint//; userNames()
      })
    }
    contents = panel
    userNames()

    reactions += {
      case WindowClosing(e) => System.exit(0)
    }

    def userNames(): Unit = {
      val w = userNameDialog(White); if (w.isDefined) White.playerName = w.get
      var b = userNameDialog(Black); if (b.isDefined) Black.playerName = b.get
    }

    def userNameDialog(color: Color): Option[String] = showInput(contents.head,
      color.toString, "Player names", Message.Plain, Swing.EmptyIcon, Nil, color.playerName)
  }
}
