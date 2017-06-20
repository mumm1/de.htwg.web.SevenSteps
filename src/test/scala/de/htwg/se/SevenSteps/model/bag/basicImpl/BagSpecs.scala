package de.htwg.se.SevenSteps.model.bag.basicImpl

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class BagSpecs extends WordSpec {

  "A new Bag" should {
    "have a insert function" in {
      val bag = Bag(random = false, colors = List('a', 'b'))
      bag.insert('a')
      bag.insert('b')
      bag.insert('c')
      bag.bag should be(ListBuffer('a', 'b', 'c'))
    }
    "have a reset function" in {
      val bag = Bag(random = false, colors = List('a', 'b'))
      bag.insert('a')
      bag.reset.bag should be(ListBuffer())
    }
    "have a draw function" in {
      val bag = Bag(random = false, colors = List('a', 'b'))
      bag.insert('a')
      bag.get() should be(Some('a'))
      bag.get() should be(None)
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
  }
  "A bag with a color List and random" should {
    val bag2 = Bag(random = true, colors = List('a'))

    "have 7 stones"in {
      bag2.fillup()
      bag2.getStoneNumber should be(7)
      bag2.bag.length should be (7)    }
    "should be not empty" in {
      bag2.isEmpty should be(false)
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
    "bag should be empty now" in {
      bag2.getStoneNumber should be(0)
      bag2.isEmpty should be(true)
    }
  }
}