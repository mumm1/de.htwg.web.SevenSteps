package de.htwg.se.SevenSteps.aview.gui

import scala.swing._
import javax.swing.table._
import scala.swing.event._
import de.htwg.se.SevenSteps.controller.{Controller, Prepare}
import scala.xml.dtd.ContentModelParser

class CellPanel(row: Int, col: Int, controller: Controller, curColorChar: Char) extends FlowPanel {
  val curColor = Converter.char2Color(curColorChar)
  val cell = controller.grid.cell(row, col).get
  val label =
    new Label {
      if (cell.color != ' ') {
        text = cell.height.toString
        font = new Font("Verdana", 1, 36)
      }
    }
  val field = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(51, 51)
    listenTo(mouse.clicks)
    self.setBackground(Converter.char2Color(cell.color))
    if (!controller.gameState.isInstanceOf[Prepare])
    contents += label

    reactions += {
      case MouseClicked(src, pt, mod, clicks, pops) => {
        if (controller.gameState.isInstanceOf[Prepare]) {
          controller.setColor(row, col, curColorChar)
        }
        else {
          controller.setStone(row, col)
        }
      }
    }
  }
  contents += field
}
