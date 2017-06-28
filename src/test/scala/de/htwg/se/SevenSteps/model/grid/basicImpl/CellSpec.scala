package de.htwg.se.SevenSteps.model.grid.basicImpl

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CellSpec extends WordSpec {
  "A Cell" should {
    val cell = Cell('a', 2)
    "have a color" in {
      cell.color should be('a')
    }
    "have a height" in {
      cell.height should be(2)
    }
    "generate a string in the form" in {
      cell.toString should be("a 2")
    }
    "have a toXML function in the form" in {
      cell.toXML().toString() should be("<cell colorCell=\"a\" height=\"2\"></cell>")

    }
  }
}
