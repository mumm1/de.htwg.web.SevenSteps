package de.htwg.se.SevenSteps.model.impl

import de.htwg.se.SevenSteps.model.IBag

/**
  * Created by acer1 on 04.05.2017.
  */
case class Bag(var bag: Array[Char] = new Array[Char](0),
               var entfernt: Int = 0,
               var aktuell: Int = 0,
               var random: Boolean,
               var colors: List[Char] = List[Char]())
  extends IBag {
  def fillup(): Unit = {
    for ((c) <- colors) {
      for (i <- 0 to 6)
        insert(c)
    }
  }
  def insert(x: Char): Unit = {
    var bag2 = new Array[Char](bag.length + 1)
    var i = 0
    while (i < bag.length) {
      bag2(i) = bag(i)
      i += 1
    }
    bag2(bag2.length - 1) = x
    bag = bag2
  }
  def get(m: Int): Option[Array[Char]] = { //Zieht eine vorgegebene Anzahl von Objekten aus dem Pool
    var bag3 = new Array[Char](m)
    var i = 0
    while (i < m) {
      get() match {
        case Some(col: Char) => bag3(i) = col
        case None => return None
      }
      i += 1
    }
    Some(bag3) //Rückgabe des Arrays mit den gezogenen Werten
  }
  def get(): Option[Char] = { //Zieht ein Objekt aus dem Pool
    var rand = 0.35
    if (random)
      rand = Math.random()
    var tmp = 'a'
    var Ausgabe = 'a'
    if (bag.length >= (entfernt + 1)) {
      aktuell = bag.length - entfernt
      // val gezogen = (Math.random * aktuell).asInstanceOf[Int]
      val gezogen = (rand * aktuell).asInstanceOf[Int]
      Ausgabe = bag(gezogen)
      tmp = bag(gezogen) //Gezogenen Wert mit dem Wert am Ende des Feldes
      bag(gezogen) = bag(bag.length - (entfernt + 1)) //vertauschen
      bag(bag.length - entfernt - 1) = tmp
      entfernt += 1
      Some(Ausgabe) //gezogenen Wert Ausgeben
    }
    else None
  }
  def isEmpty(): Boolean = {
    bag.length >= (entfernt + 1)
  }
  def copy1(newColors: List[Char]): Bag = {
    copy(colors = newColors)
  }
}