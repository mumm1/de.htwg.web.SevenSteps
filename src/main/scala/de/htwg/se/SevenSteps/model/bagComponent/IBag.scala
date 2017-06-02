package de.htwg.se.SevenSteps.model.bagComponent

/**
  * Created by acer1 on 18.05.2017.
  */
trait IBag {
  def get(): Option[Char]
  def isEmpty(): Boolean
  def fillup(): Unit
  def copy1(newColors: List[Char]): IBag
  def getStoneNumber(): Int
}
