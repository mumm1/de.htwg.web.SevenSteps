package de.htwg.se.SevenSteps.aview.gui

import javax.swing.JPopupMenu

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.util.Observer

import scala.swing._
import scala.swing.BorderPanel.Position._
import event._
import java.awt.{Color, Graphics2D}

import de.htwg.se.SevenSteps.controller.controllerBasicImpl.Controller

import scala.util.Random
import scala.io.Source._

class PopupMenu extends Component {
  override lazy val peer: JPopupMenu = new JPopupMenu
  def add(item: MenuItem): Unit = {
    peer.add(item.peer)
  }
  def setVisible(visible: Boolean): Unit = {
    peer.setVisible(visible)
  }
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

class SwingGui(controller: IController) extends Frame with Observer {
  title = "Seven Steps"
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
      contents += new MenuItem(Action("Prepare Game") {
        new PrepareWindow(controller)
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
  def playerPanel: BorderPanel = {

    val colorP = new GridPanel(controller.players.length, controller.grid.getColors.length) {
      for {x <- 0 until controller.players.length; color <- controller.grid.getColors} {
        val player = controller.players(x)
        player.map match {
          case None => contents += new Label("")
          case Some(m) => contents += new Label {
            text = " " + m(color).toString + " "
            foreground = ColorManager.char2Color(color)
            font = new Font("Verdana", 1, 20)
          }
        }
      }
    }
    val playerP = new GridPanel(controller.players.length,1){
      for {x <- 0 until controller.players.length} {
        val player = controller.players(x)
        if (player == controller.players.getCurPlayer) {
          contents += new Label {
            text = player.name + " " + player.points + " "
            font = new Font("Verdana", 1, 20)
          }
        } else {
          contents += new Label {
            text = player.name + "  " + player.points + " "
          }
        }
      }
    }

    new BorderPanel {
      add(playerP, BorderPanel.Position.West)
      add(colorP, BorderPanel.Position.East)
      add(new Label{text="Bag: "+controller.bag.getStoneNumber();font = new Font("Verdana", 1, 20)},BorderPanel.Position.North)
    }
  }
  visible = true
  def getButtonColor(color: Char): MenuItem = {
    val newItem = new MenuItem(Action("       ") {
      ColorManager.curColer = color
    })
    newItem.background = ColorManager.char2Color(color)
    newItem
  }
}

class PrepareWindow(controller: IController) extends MainFrame {
  title = "Prepare Game"
  val nameField = new TextField(" ", 20)
  val rows = new TextField(" ", 5) {
    listenTo(keys)
    reactions += { case e: KeyTyped =>
      if (!e.char.isDigit) e.consume
    }
  }
  val col = new TextField(" ", 5) {
    listenTo(keys)
    reactions += { case e: KeyTyped =>
      if (!e.char.isDigit) e.consume
    }
  }
  val gridPanel = new FlowPanel() {
    contents += new Label("Columns:")
    contents += col
    contents += new Label("Rows:")
    contents += rows
    contents += Button("Create") {
      val rowNum = rows.text.trim.toInt
      val colNum = col.text.trim.toInt
      controller.newGrid(" " * rowNum * colNum, colNum)
      close()
    }
  }
  val playerPanel = new FlowPanel() {
    contents += new Label("Name:")
    contents += nameField
    contents += Button("Add") {
      controller.addPlayer(nameField.text)
    }
  }
  val mainPanel = new BorderPanel() {
    add(playerPanel, BorderPanel.Position.West)
    add(gridPanel, BorderPanel.Position.East)
  }
  contents = mainPanel
  visible = true
}