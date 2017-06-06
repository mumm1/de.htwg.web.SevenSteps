package de.htwg.se.SevenSteps.model.bag.mockImpl

import de.htwg.se.SevenSteps.model.bag.IBag

case class Bag() extends IBag {
  def get(): Option[Char] = None
  def isEmpty: Boolean = true
  def fillup(): Unit = ()
  def copy1(newColors: List[Char]): IBag = this
  def getStoneNumber: Int = 0
}
