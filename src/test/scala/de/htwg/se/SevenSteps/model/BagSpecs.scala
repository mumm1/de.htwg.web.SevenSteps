package de.htwg.se.SevenSteps.model

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable.ListMap

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
    "have a draw function" in {
      bag2.get() should be ('b')
    }
    "have less stones after draw" in {
      bag2.bag should be (bag3_after_1draw)
    }
    "can draw 2 times" in {
      bag2.get() should be ('a')
    }
    "have less stones second after second draw" in {
      bag2.bag should be (bag3_after_2draw)
    }

    "can draw 3 times" in {
      bag2.get() should be ('c')
    }
    "have less stones second after third draw" in {
      bag2.bag should be (bag3_after_3draw)
    }
    "cant draw 4 times" in {
      bag2.get() should be ('$')
    }
  }

  "A second Bag " should {
    val bag2 =  Bag(random = false, colors = List('a','b'))
    bag2.insert('a')
    bag2.insert('b')
    bag2.insert('c')
    "have a function to draw 2 stones" in {
      bag2.get(2) should be(Array('b', 'a'))
    }
    "can draw the last Stone" in {
      bag2.get() should be ('c')
    }
    "cant draw another stone" in {
      bag2.get() should be ('$')
    }
  }

  "A third Bag " should {
    val bag2 =  Bag(random = false, colors = List('a','b'))
    bag2.insert('a')
    bag2.insert('b')
    bag2.insert('c')
    "can draw all stones" in {
      bag2.get(3) should be(Array('b', 'a','c'))
    }
    "cant draw another stone" in {
      bag2.get() should be ('$')
    }
  }

  "Another Bag " should {
    val bag2 = Bag(random = false, colors = List('a','b'))
    bag2.insert('a')
    bag2.insert('b')
    bag2.insert('c')
    "have a function to draw 2 stones" in {
      bag2.get(2) should be(Array('b', 'a'))
    }
    "can draw the last Stone" in {
      bag2.get() should be ('c')
    }
    "cant draw another stone" in {
      bag2.get() should be ('$')
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
      bag2.get(2) should be (Array('a','a'))
    }
    "cant draw antoher stone" in {
      bag2.get() should be ('$')
    }
    "can draw another stone after insert" in {
      bag2.insert('a')
      bag2.get() should be ('a')
    }
  }

  "A bag with a color List" should {
    val bag2 =  Bag(random = false, colors = List('a'))

    "have 7 stones"in {
      bag2.fillup()
      bag2.bag.length should be (7)
    }
    "can draw 7 stones" in {
      bag2.get(7) should be (Array('a','a','a','a','a','a','a'))
    }

  }
  "A bag with a color List and random" should {
    val bag2 = Bag(random = true, colors = List('a'))

    "have 7 stones"in {
      bag2.fillup()
      bag2.bag.length should be (7)    }
    "can draw 7 stones" in {
      bag2.get(7) should be (Array('a','a','a','a','a','a','a'))
    }

  }
}