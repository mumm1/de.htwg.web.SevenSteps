package de.htwg.se.SevenSteps.model.gridComponent.gridMocImpl

import de.htwg.se.SevenSteps.model.gridComponent.ICell

case class Cell() extends ICell {
  def color: Char = 'b'
  def height: Int = 2
}

