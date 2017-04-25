package de.htwg.se.SevenSteps.model

case class Cell(color: Char = ' ', height: Int = 0) {
  override def toString: String = {
    if (color != ' ') {
      color.toString + " " + height
    } else {
      "   "
    }
  }
}

