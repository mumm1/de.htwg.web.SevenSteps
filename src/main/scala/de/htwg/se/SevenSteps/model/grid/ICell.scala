package de.htwg.se.SevenSteps.model.grid

trait ICell {
  def color: Char
  def height: Int
  def toXML(): scala.xml.Elem
}
