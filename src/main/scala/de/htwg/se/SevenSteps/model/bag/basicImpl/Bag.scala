package de.htwg.se.SevenSteps.model.bag.basicImpl

import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.model.bag.IBag

import scala.collection.mutable

case class Bag @JsonCreator()(var bag: Vector[Char],
                              var colors: Vector[Char])
  extends IBag {
  def fillup(): Unit = {
    val randomList: mutable.ListBuffer[Char] = mutable.ListBuffer()
    for ((c) <- colors) {
      for (i <- 0 to 6) {
        val rand = (Math.random() * (randomList.length - 1)).asInstanceOf[Int]
        randomList.insert(rand, c)
      }
    }
    bag = randomList.toVector
  }
  def insert(x: Char): Unit = bag :+= x
  def get(): Option[Char] = {
    if (bag.nonEmpty) {
      val result = bag.last
      bag = bag.init
      Some(result)
    }
    else {
      None
    }
  }
  def isEmpty: Boolean = bag.isEmpty
  def getStoneNumber: Int = bag.length
  def reset: Bag = new Bag()
  def this() = this(Vector[Char](), Vector[Char]())
  def copy1(newColors: List[Char]): Bag = {
    copy(colors = newColors.toVector)
  }
  override def equals(that: Any): Boolean =
    that match {
      case that: Bag => colors.sameElements(that.colors) //&&bag.sameElements(that.bag)
      case _ => false
    }
  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + bag.hashCode();
    result = prime * result + colors.hashCode()
    result
  }
}
