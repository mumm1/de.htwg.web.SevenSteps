package de.htwg.se.SevenSteps.aview.gui

import javax.swing.JPopupMenu
import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.util.Observer

import scala.io.Source._


class PopupMenu extends Component
{
  override lazy val peer : JPopupMenu = new JPopupMenu

  def add(item:MenuItem) : Unit = { peer.add(item.peer) }
  def setVisible(visible:Boolean) : Unit = { peer.setVisible(visible) }
  /* Create any other peer methods here */
}

object ColorManager {
  var curColer = ' '
  def getCurColer: Color = char2Color(curColer)
  def char2Color(c: Char): Color = {
    c match {
      case 'a' => java.awt.Color.LIGHT_GRAY
      case 'b' => java.awt.Color.BLUE
      case 'g' => java.awt.Color.GREEN
      case 'r' => java.awt.Color.RED
      case 'y' => java.awt.Color.YELLOW
      case 'o' => java.awt.Color.ORANGE
      case _ => java.awt.Color.WHITE
    }
  }
}

class SwingGui(controller: Controller) extends Frame with Observer{

  controller.add(this)
  override def update(): Unit = redraw()
  def redraw() = {
    contents = new BorderPanel {

      add(playerPanel, BorderPanel.Position.West)
 //     add(new TextArea(controller.players.toString), BorderPanel.Position.West)
      add(gridPanel, BorderPanel.Position.Center)
      add(new TextField(controller.message, 20), BorderPanel.Position.North)
    }
  }



  contents = new BorderPanel {
    add(new TextArea(controller.players.toString), BorderPanel.Position.West)
    add(gridPanel, BorderPanel.Position.Center)
    add(new TextField(controller.message, 20), BorderPanel.Position.North)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New Grid") { controller.newGrid("aabbbbaababb  bab ba ba ",7) })
      contents += new MenuItem(Action("Add Player") { controller.addPlayer("Hans") })
      contents += new MenuItem(Action("Start Game") { controller.startGame() })
      contents += new MenuItem(Action("Next Player") { controller.nextPlayer() })
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo() })
      contents += new MenuItem(Action("Redo") { controller.redo() })
    }
    contents += new Menu("GridColors") {
      mnemonic = Key.G
      contents += getButtonColor('a')
      contents += getButtonColor('g')
      contents += getButtonColor('b')
      contents += getButtonColor('r')
      contents += getButtonColor('y')
      contents += getButtonColor('o')
      contents += getButtonColor(' ')
    }
  }
  def gridPanel: GridPanel = new GridPanel(controller.grid.rows, controller.grid.cols) {
    for {x <- 0 until controller.grid.rows; y <- 0 until controller.grid.cols} {
      contents += new CellPanel(x, y, controller)
    }
  }
  def getButtonColor(color:Char):MenuItem = {
    val newItem = new MenuItem(Action("       "){ColorManager.curColer = color})
    newItem.background = ColorManager.char2Color(color)
    newItem
  }
  visible = true

  def playerPanel: GridPanel = new GridPanel(controller.players.length, controller.grid.getColors.length + 1) {
    for {x <- 0 until controller.players.length;color <-  '?'::controller.grid.getColors} {
      val player = controller.players(x)
      if(color=='?'){
        if(player==controller.players.getCurPlayer){
          contents += new Label{text=player.name;font = new Font("Arial", 0, 20)}
        }else{
          contents += new Label{text=player.name}
        }
      }else{
        player.map match {
          case None => contents += new Label("")
          case Some(m) => contents += new Label{text=m(color).toString;foreground=ColorManager.char2Color(color)}
        }
      }
    }
  }
}