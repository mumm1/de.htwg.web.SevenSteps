package de.htwg.se.SevenSteps.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.SevenSteps.controller._
import scala.io.Source._


class SwingGui(controller: Controller) extends Frame {

//  listenTo(controller)

  def gridPanel : GridPanel = new GridPanel(controller.grid.rows, controller.grid.cols) {
    for {x <- 0 until controller.grid.rows ; y <- 0 until controller.grid.cols} {
      contents += new Button {
        action = Action(controller.grid.cell(x,y).get.toString()){ controller.setStone(x,y)}
      }
    }
  }

  contents = new BorderPanel {
//    add(buton, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(new TextField(controller.message, 20), BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New Grid") { controller.newGrid("aabbbbaababb  bab ba ba ",7) })
      contents += new MenuItem(Action("Add Player") { controller.addPlayer("Hans") })
      contents += new MenuItem(Action("Start Game") { controller.startGame() })
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo() })
      contents += new MenuItem(Action("Redo") { controller.redo() })
    }
  }
}