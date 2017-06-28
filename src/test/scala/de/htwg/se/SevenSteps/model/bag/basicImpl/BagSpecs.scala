package de.htwg.se.SevenSteps.model.bag.basicImpl

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class BagSpecs extends WordSpec {
  def getBag: Bag = Bag(Vector(), Vector("a", "b"))
  "A Bag" should {
    "have a insert function" in {
      val bag = getBag
      bag.insert('a')
      bag.insert('b')
      bag.insert('c')
      bag.bag should be(ListBuffer("a", "b", "c"))
    }
    "have a reset function" in {
      val bag = getBag
      bag.insert('a')
      bag.reset.bag should be(ListBuffer())
    }
    "have a draw function" in {
      val bag = getBag
      bag.insert('a')
      bag.get() should be(Some('a'))
      bag.get() should be(None)
    }
    "can fillUp with the colors" in {
      val bag = getBag
      bag.fillup()
    }
    "can get the stone number" in {
      val bag = getBag
      bag.getStoneNumber should be(0)
      bag.insert('a')
      bag.getStoneNumber should be(1)
    }
    "have a toXML functions" in {
      val bag = getBag
      bag.insert('a')
      bag.insert('b')
      bag.insert('c')
      bag.toXML().toString() should be("<alles a='<blub bag2=\"a\"></blub><blub bag2=\"b\"></blub><blub" +
        " bag2=\"c\"></blub>' b='<bag col=\"a\"></bag><bag col=\"b\"></bag>'></alles>")
    }
  }
}