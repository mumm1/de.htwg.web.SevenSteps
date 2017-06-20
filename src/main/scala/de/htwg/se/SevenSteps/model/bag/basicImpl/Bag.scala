package de.htwg.se.SevenSteps.model.bag.basicImpl

import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.model.bag.IBag
import scala.collection.mutable

/**
  * Created by acer1 on 04.05.2017.
  */
case class Bag @JsonCreator()(var bag: mutable.ListBuffer[Char] = new mutable.ListBuffer(),
                              var entfernt: Int = 0,
                              var aktuell: Int = 0,
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
    var tmp = 'a'
    var Ausgabe = 'a'
    if (bag.length >= (entfernt + 1)) {
      aktuell = bag.length - entfernt
      val gezogen = (rand * aktuell).asInstanceOf[Int]
      Ausgabe = bag(gezogen)
      tmp = bag(gezogen)
      bag(gezogen) = bag(bag.length - (entfernt + 1))
      bag(bag.length - entfernt - 1) = tmp
      entfernt += 1
      Some(Ausgabe)
    }
    else None
  }
  def isEmpty: Boolean = {
    !(bag.length >= entfernt + 1)
  }
  def getStoneNumber: Int = {
    bag.length - entfernt
  }
  def reset: Bag = Bag(random=this.random)
  def copy1(newColors: List[Char]): Bag = {
    copy(colors = newColors)
  }
}
