package de.htwg.se.SevenSteps.model.impl

import de.htwg.se.SevenSteps.model.ICell

case class Cell(color: Char = ' ', height: Int = 0) extends ICell {
  override def toString: String = {
    if (color != ' ') {
      color.toString + " " + height
    } else {
      "   "
    }
  }
}

