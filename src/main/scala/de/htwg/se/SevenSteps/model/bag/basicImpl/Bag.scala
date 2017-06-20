package de.htwg.se.SevenSteps.model.bag.basicImpl

import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.model.bag.IBag

import scala.collection.mutable

/**
  * Created by acer1 on 04.05.2017.
  */
case class Bag @JsonCreator()(var bag: mutable.ListBuffer[Char] = new mutable.ListBuffer(),
                              var random: Boolean,
                              var colors: List[Char] = List[Char]())
  extends IBag {
  def this() = this(random=true)
  def fillup(): Unit = {
    for ((c) <- colors) {
      for (i <- 0 to 6)
        insert(c)
    }
  }
  def insert(x: Char): Unit = bag :+= x
  def get(): Option[Char] = {
    var rand = 0.35
    if (random) {
      rand = Math.random()
    }
    if (bag.nonEmpty) {
      Some(bag.remove((rand * (bag.length - 1)).asInstanceOf[Int]))
    }
    else {
      None
    }
  }
  def isEmpty: Boolean = bag.isEmpty
  def getStoneNumber: Int = bag.length
  def reset: Bag = Bag(random=this.random)
  def copy1(newColors: List[Char]): Bag = {
    copy(colors = newColors)
  }
}
