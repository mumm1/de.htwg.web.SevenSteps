package de.htwg.se.SevenSteps.model.bag.mockImpl

import de.htwg.se.SevenSteps.model.bag.IBag
import scala.xml.Elem

case class BagMock() extends IBag {
  def get(): Option[Char] = None
  def isEmpty: Boolean = true
  def fillup(): Unit = ()
  def copy1(newColors: List[Char]): IBag = this
  def getStoneNumber: Int = 0
  def reset: IBag = this
  def toXML: Elem = <el el=""></el>
}
