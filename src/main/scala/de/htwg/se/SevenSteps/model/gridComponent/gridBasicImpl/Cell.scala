package de.htwg.se.SevenSteps.model.gridComponent.gridBasicImpl

import de.htwg.se.SevenSteps.model.gridComponent.ICell

case class Cell(color: Char = ' ', height: Int = 0) extends ICell {
  override def toString: String = {
    if (color != ' ') {
      color.toString + " " + height
    } else {
      "   "
    }
  }
}

