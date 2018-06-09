package chessGui

import gameLogic.GameManager
import structures.{Black, Color, White}

import scala.swing.Dialog.{Message, showInput}
import scala.swing.event.WindowClosing
import scala.swing.{Action, Button, MainFrame, MenuBar, SimpleSwingApplication, Swing}

/*TODO
GUI
- okno zakonczenia gry
- promocja piona

Game Manager
- zakocznie gry

Action Manager
- ruch na ukos nie działa
- bicie w przelocie
- promocja
- roszada
*/

object Appl extends GuiApp
  class GuiApp extends SimpleSwingApplication{
    def top: MainFrame = new MainFrame {
      val panel = new GamePanel
      title = "Chess"

      menuBar = new MenuBar{
        contents += new Button(Action("New Game") {
          panel.reset()
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
