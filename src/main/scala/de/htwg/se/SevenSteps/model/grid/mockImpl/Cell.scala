package de.htwg.se.SevenSteps.model.grid.mockImpl

import de.htwg.se.SevenSteps.model.grid.ICell

case class Cell() extends ICell {
  def color: Char = 'b'
  def height: Int = 2
}

