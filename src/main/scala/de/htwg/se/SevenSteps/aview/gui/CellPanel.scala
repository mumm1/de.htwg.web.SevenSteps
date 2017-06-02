package de.htwg.se.SevenSteps.aview.gui

import scala.swing._

import scala.swing.event._
import de.htwg.se.SevenSteps.controller.{IController, IPrepare}

class CellPanel(row: Int, col: Int, controller: IController) extends FlowPanel {
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
    self.setBackground(ColorManager.char2Color(cell.color))
    if (!controller.gameState.isInstanceOf[IPrepare])
    contents += label

    reactions += {
      case MouseClicked(src, pt, mod, clicks, pops) => {
        if (controller.gameState.isInstanceOf[IPrepare]) {
          controller.setColor(row, col, ColorManager.curColer)
          self.setBackground(ColorManager.getCurColer)
        }
        else {
          controller.setStone(row, col)
        }
      }
    }
  }
  contents += field
}


