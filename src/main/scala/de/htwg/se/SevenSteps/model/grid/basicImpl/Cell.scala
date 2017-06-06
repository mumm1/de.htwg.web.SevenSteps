package de.htwg.se.SevenSteps.model.grid.basicImpl

import de.htwg.se.SevenSteps.model.grid.ICell

case class Cell(color: Char = ' ', height: Int = 0) extends ICell {
  override def toString: String = {
    if (color != ' ') {
      color.toString + " " + height
    } else {
      "   "
    }
  }
}

