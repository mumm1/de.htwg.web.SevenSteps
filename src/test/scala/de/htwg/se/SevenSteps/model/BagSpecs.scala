package de.htwg.se.SevenSteps.model

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable.ListMap

@RunWith(classOf[JUnitRunner])
class BagSpec extends WordSpec {
  "A new Bag with a name" should {
    val bag = new Bag("Sack")
    "have a name" in {
      bag.name should be("Sack")
    }
    "have a empty stone Map" in {
      bag.stones.isEmpty should be(true)
    }
    "have a empty color List" in {
      bag.colors.isEmpty should be(true)
    }
  }
  "A Bag with a color List" should {
    var bag = new Bag("Sack")
    bag = bag.copy(colors = List('a', 'b'))
    "have a name" in {
      bag.name should be("Sack")
    }
    "have colors" in {
      bag.colors.apply(0) should be('a')
      bag.colors.apply(1) should be('b')
      bag.colors.isEmpty should be(false)
    }
    "have a empty stone Map" in {
      bag.stones.isEmpty should be(true)
    }
  }
  "A Bag with fillup() without colors" should {
    var bag = new Bag("Sack")
    bag.fillup()
    val color_char = bag.pull()
    "have a empty stone Map" in {
      bag.stones.isEmpty should be(true)
    }
    "have a name" in {
      bag.name should be("Sack")
    }
    "have  empty colors" in {
      bag.colors.isEmpty should be(true)
    }
    "have a $ color char" in {
      bag.pull() should be('$')
    }
  }
  "A filled up bag with colors" should {
    var bag = new Bag("Sack")
    bag = bag.copy(colors = List('a'))
    bag.fillup()
    val color_char = bag.pull()
    "have a stone Map with 19 stones after pull" in {
      bag.stones.isEmpty should be(false)
      bag.stones.apply('a') should be(19)
    }
    "have a name" in {
      bag.name should be("Sack")
    }
    "have colors" in {
      bag.colors.isEmpty should be(false)
      bag.colors.apply(0) should be('a')
    }
    "have a $ color char" in {
      color_char should be('a')
    }
  }
  "A Bag with all" should {
    val map = new ListMap[Char, Int]()
    val col: List[Char] = List('b')
    map.updated('b', 15)
    var bag = new Bag("Sack", map, col)
    bag.fillup()
    //			bag = bag.copy(colors = List('a'))
    //		bag.fillup()
    //		val color_char = bag.pull()

    "have stones" in {
      bag.stones.isEmpty should be(false)
      bag.stones.apply('b') should be(20)
    }
    "have colors" in {
      bag.colors.isEmpty should be(false)
    }
  }

}