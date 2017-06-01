package de.htwg.se.SevenSteps.aview.gui

import scala.swing._
import javax.swing.table._

import scala.swing.event._
import de.htwg.se.SevenSteps.controller.{Controller, Prepare}

import scala.xml.dtd.ContentModelParser

class CellPanel(row: Int, col: Int, controller: Controller) extends FlowPanel {
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
    self.setBackground(char2Color(cell.color))
    contents += label

    reactions += {
      case MouseClicked(src, pt, mod, clicks, pops) => {
        if (controller.gameState.isInstanceOf[Prepare]) {
          controller.setColor(row, col, curColor)
        }
        else {
          if (cell.color != ' ') {
            cell.height.toString
          } else {
            ""
          }
          controller.setStone(row, col)
        }
      }
    }
  }
  var curColor = ' '
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
  contents += field
}
