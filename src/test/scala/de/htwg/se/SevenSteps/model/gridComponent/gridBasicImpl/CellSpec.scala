package de.htwg.se.SevenSteps.model.gridComponent.gridBasicImpl

import de.htwg.se.SevenSteps.model.gridComponent.gridBasicImpl.Cell
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
  }
}
