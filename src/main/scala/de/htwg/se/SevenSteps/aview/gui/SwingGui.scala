package de.htwg.se.SevenSteps.aview.gui

import javax.swing.JPopupMenu
import de.htwg.se.SevenSteps.aview.gui._
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


class SwingGui(controller: Controller) extends Frame with Observer{

  controller.add(this)
  var curColor = ' '
  override def update(): Unit = redraw()
  def redraw() = {
    contents = new BorderPanel {
      add(new TextArea(controller.players.toString), BorderPanel.Position.West)
      add(gridPanel, BorderPanel.Position.Center)
      add(new TextField(controller.message, 20), BorderPanel.Position.North)
    }
  }
  def gridPanel : GridPanel = new GridPanel(controller.grid.rows, controller.grid.cols) {
    for {x <- 0 until controller.grid.rows ; y <- 0 until controller.grid.cols} {
      contents += new CellPanel(x, y, controller)

    }
  }

  contents = new BorderPanel {
    add(new TextArea(controller.players.toString), BorderPanel.Position.West)
    add(gridPanel, BorderPanel.Position.Center)
    add(new TextField(controller.message, 20), BorderPanel.Position.North)
  }
  def newMenuBar: MenuBar = {
    new MenuBar {
      contents += new Menu("File") {
        mnemonic = Key.F
        contents += new MenuItem(Action("New Grid") {
          controller.newGrid("aabbbbaababb  bab ba ba ", 7)
        })
        contents += new MenuItem(Action("Add Player") {
          controller.addPlayer("Hans")
        })
        contents += new MenuItem(Action("Start Game") {
          controller.startGame()
        })
        contents += new MenuItem(Action("Next Player") {
          controller.nextPlayer()
        })
        contents += new MenuItem(Action("Quit") {
          System.exit(0)
        })
      }
      contents += new Menu("Edit") {
        mnemonic = Key.E
        contents += new MenuItem(Action("Undo") {
          controller.undo()
        })
        contents += new MenuItem(Action("Redo") {
          controller.redo()
        })
      }
      contents += new Menu("Grid Colors") {
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
    contents += new Menu("Grid Colors") {
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
  def getButtonColor(color:Char):MenuItem = {
    val newItem = new MenuItem(Action("       "){curColor = color})
    newItem.background=char2Color(color)
    newItem
  }
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
  visible = true
}