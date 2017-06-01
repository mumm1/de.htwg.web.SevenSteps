package de.htwg.se.SevenSteps.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.util.Observer

import scala.io.Source._


class SwingGui(controller: Controller) extends Frame with Observer{

  controller.add(this)
  override def update(): Unit = redraw()

  def redraw() = {
    contents = new BorderPanel {
      add(grid, BorderPanel.Position.Center)
      add(new TextField(controller.message, 20), BorderPanel.Position.South)
      //add(new TextField(if(controller.getCurPlayer != null){ controller.getCurPlayer.toString}else{"dddd"}, 20), BorderPanel.Position.East)


    }
  }
  def grid: GridPanel = new GridPanel(controller.grid.rows, controller.grid.cols) {
    for {x <- 0 until controller.grid.rows ; y <- 0 until controller.grid.cols} {
      contents += new CellPanel(x, y, controller)
    }
  }



  contents = new BorderPanel {
//    add(buton, BorderPanel.Position.North)
    add(grid, BorderPanel.Position.Center)
    add(new TextField(controller.message, 20), BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New Grid") {
        controller.newGrid("aabbbbaababbbabbaba ", 4)
      })
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
  }

  visible = true
}