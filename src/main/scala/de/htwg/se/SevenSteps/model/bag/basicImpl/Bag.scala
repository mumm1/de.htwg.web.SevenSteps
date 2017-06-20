package de.htwg.se.SevenSteps.model.bag.basicImpl

import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.model.bag.IBag

import scala.collection.mutable

case class Bag @JsonCreator()(var bag: Vector[String],
                              var colors: Vector[String])
  extends IBag {
  def fillup(): Unit = {
    val randomList: mutable.ListBuffer[String] = mutable.ListBuffer()
    for ((c) <- colors) {
      for (i <- 0 to 6) {
        val rand = (Math.random() * (randomList.length - 1)).asInstanceOf[Int]
        randomList.insert(rand, c.toString)
      }
    }
    bag = randomList.toVector
  }
  def insert(x: Char): Unit = bag :+= x.toString
  def get(): Option[Char] = {
    if (bag.nonEmpty) {
      val result = bag.last
      bag = bag.init
      Some(result.charAt(0))
    }
    else {
      None
    }
  }
  def isEmpty: Boolean = bag.isEmpty
  def getStoneNumber: Int = bag.length
  def reset: Bag = new Bag()
  def this() = this(Vector[String](), Vector[String]())
  def copy1(newColors: List[Char]): Bag = {
    val newList = for (c <- newColors) yield {
      c.toString
    }
    copy(colors = newList.toVector)
  }
}
