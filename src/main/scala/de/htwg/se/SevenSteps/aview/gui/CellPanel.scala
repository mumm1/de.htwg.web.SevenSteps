package de.htwg.se.SevenSteps.aview.gui

import de.htwg.se.SevenSteps.controller.{IController, IPrepare}

import scala.swing._
import scala.swing.event._

class CellPanel(row: Int, col: Int, controller: IController) extends FlowPanel {
  val cell = controller.state.grid.cell(row, col).get
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
    if (!controller.state.gameState.isInstanceOf[IPrepare])
    contents += label

    reactions += {
      case MouseClicked(src, pt, mod, clicks, pops) => {
        if (controller.state.gameState.isInstanceOf[IPrepare]) {
          controller.setColor(row, col, ColorManager.curColer)
        }
        else {
          controller.setStone(row, col)
        }
      }
    }
  }
  contents += field
}


