package de.htwg.se.SevenSteps.model.bagComponent.bagMocImpl

import de.htwg.se.SevenSteps.model.bagComponent.IBag

case class BagMoc() extends IBag {
  def get(): Option[Char] = None
  def isEmpty(): Boolean = true
  def fillup(): Unit = ()
  def copy1(newColors: List[Char]): IBag = this
  def getStoneNumber(): Int = 0
}
