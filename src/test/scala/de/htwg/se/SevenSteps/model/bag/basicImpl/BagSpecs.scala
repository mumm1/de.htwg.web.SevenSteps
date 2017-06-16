package de.htwg.se.SevenSteps.model.bag.basicImpl

import de.htwg.se.SevenSteps.model.bag.basicImpl.Bag
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BagSpecs extends WordSpec {

  "A new Bag" should {
    val bag2 = Bag(random = false, colors = List('a','b'))
    val bag2_copy = bag2
    bag2.insert('a')
    bag2.insert('b')
    bag2.insert('c')
    val bag3 = Array('a', 'b', 'c')
    val bag3_after_1draw = Array('a','c','b')
    val bag3_after_2draw = Array('c','a','b')
    val bag3_after_3draw = Array('c','a','b')
    "have a insert function" in {
      bag2.bag should be (bag3)
    }
    "have a reset function" in {
      bag2.reset.isInstanceOf[Bag] should be(true)
    }
    "have a draw function" in {
      bag2.get() should be(Some('b'))
    }
    "have less stones after draw" in {
      bag2.bag should be (bag3_after_1draw)
    }
    "can draw 2 times" in {
      bag2.get() should be(Some('a'))
    }
    "have less stones second after second draw" in {
      bag2.bag should be (bag3_after_2draw)
    }

    "can draw 3 times" in {
      bag2.get() should be(Some('c'))
    }
    "have less stones second after third draw" in {
      bag2.bag should be (bag3_after_3draw)
    }
    "cant draw 4 times" in {
      bag2.get() should be(None)
    }
  }

  "A second Bag " should {
    val bag2 =  Bag(random = false, colors = List('a','b'))
    bag2.insert('a')
    bag2.insert('b')
    bag2.insert('c')
    "have a function to draw 2 stones" in {
      bag2.get(2) match {
        case Some(n) => n should be(Array('b', 'a'))
        case None =>
      }
    }
    "can draw the last Stone" in {
      bag2.get() should be(Some('c'))
    }
    "cant draw another stone" in {
      bag2.get() should be(None)
    }
  }

  "A third Bag " should {
    val bag2 =  Bag(random = false, colors = List('a','b'))
    bag2.insert('a')
    bag2.insert('b')
    bag2.insert('c')
    "can draw all stones" in {
      bag2.get(3) match {
        case Some(n) => n should be(Array('b', 'a', 'c'))
        case None =>
      }
    }
    "cant draw another stone" in {
      bag2.get() should be(None)
    }
  }

  "Another Bag " should {
    val bag2 = Bag(random = false, colors = List('a','b'))
    bag2.insert('a')
    bag2.insert('b')
    bag2.insert('c')
    "have a function to draw 2 stones" in {
      bag2.get(2) match {
        case Some(n) => n should be(Array('b', 'a'))
        case None =>
      }
    }
    "can draw the last Stone" in {
      bag2.get() should be(Some('c'))
    }
    "cant draw another stone" in {
      bag2.get() should be(None)
    }
  }

  "A fourth Bag " should {
    val bag2 =  Bag(random = false, colors = List('a','b'))
    bag2.insert('a')


    "can insert a second stone" in {
      bag2.insert('a')
      bag2.bag should be (Array('a','a'))
    }
    "have a boolen" in {
      bag2.random should be (false)
    }
  }
  "A random Bag " should {
    val bag2 = Bag(random = true, colors = List('a','b'))
    bag2.insert('a')


    "can insert a second stone" in {
      bag2.insert('a')
      bag2.bag should be (Array('a','a'))
    }
    "have a boolen" in {
      bag2.random should be (true)
    }
    "can draw 2 stones " in {
      bag2.get(2) match {
        case Some(n) => n should be(Array('a', 'a'))
        case None =>
      }
    }
    "cant draw antoher stone" in {
      bag2.get() should be(None)
    }
    "can draw another stone after insert" in {
      bag2.insert('a')
      bag2.get() should be(Some('a'))
    }
  }

  "A bag with a color List" should {
    val bag2 =  Bag(random = false, colors = List('a'))

    "have 7 stones"in {
      bag2.fillup()
      bag2.bag.length should be (7)
    }
    "can draw 7 stones" in {
      bag2.get(7) match {
        case Some(n) => n should be(Array('a', 'a', 'a', 'a', 'a', 'a', 'a'))
        case None =>
      }
    }

  }
  "A bag with a color List and random" should {
    val bag2 = Bag(random = true, colors = List('a'))

    "have 7 stones"in {
      bag2.fillup()
      bag2.getStoneNumber should be(7)
      bag2.bag.length should be (7)    }
    "can draw 7 stones" in {
      bag2.get(7) match {
        case Some(n) => n should be(Array('a', 'a', 'a', 'a', 'a', 'a', 'a'))
        case None =>
      }
    }
    "should be not empty" in {
      bag2.isEmpty should be(true)
    }
    "should have a copy Colors function" in {
      val bag3 = bag2.copy1(List('a', 'b'))
      bag3.colors should be(List('a', 'b'))
    }
  }
  "A bag with a color List and random" should {
    var bag2 = Bag(random = true, colors = List('a'))
    bag2.colors = List('b')
    "should have a updated List" in {
      bag2.colors should be(List('b'))
    }
  }
  "A bag with a color List2" should {
    val bag2 = Bag(random = false, colors = List('a'))
    "can draw 7 stones" in {
      bag2.get(7) match {
        case Some(n) => n should be(Array('a', 'a', 'a', 'a', 'a', 'a', 'a'))
        case None =>
      }
    }
    "bag should be empty now" in {
      bag2.getStoneNumber should be(0)
      bag2.isEmpty should be(true)
    }
  }
}