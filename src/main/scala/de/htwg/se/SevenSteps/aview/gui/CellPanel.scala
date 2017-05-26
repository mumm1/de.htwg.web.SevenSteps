package de.htwg.se.SevenSteps.aview.gui

import scala.swing._
import javax.swing.table._
import scala.swing.event._
import de.htwg.se.SevenSteps.controller.Controller

class CellPanel(row: Int, col: Int, controller: Controller) extends FlowPanel {

  val givenCellColor = new Color(200, 200, 255)
  val cellColor = new Color(224, 224, 255)
  val highlightedCellColor = new Color(192, 255, 192)

  def cellText(row: Int, col: Int) : String = controller.grid.cell(row,col).toString

  val label =
    new Label {
      text = cellText(row, col)
      font = new Font("Verdana", 1, 36)
    }

  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(51, 51)
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    reactions += {
      case MouseClicked(src, pt, mod, clicks, pops) => {
        controller.setStone(row, col)
      }
    }
  }

}
