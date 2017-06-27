package de.htwg.se.SevenSteps.model.grid.mockImpl

import de.htwg.se.SevenSteps.model.grid.ICell
import scala.xml.Elem

case class CellMock() extends ICell {
  def color: Char = 'b'
  def height: Int = 2
  def toXML: Elem = <el el=""></el>

}

