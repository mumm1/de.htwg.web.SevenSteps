package de.htwg.se.SevenSteps.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec{
  "A new Grid with 1 empty Cell" should{
    val grid = Grid(1,1)
    
  "have the right dimensions" in {
    grid.cols should be(1)
    grid.rows should be(1)
  }
  "generate a string of the form" in {
      grid.toString should be("\n+---+\n|   |\n+---+\n")
    }
  "set a color to the cell" in {
    grid.set(0,0,"b").getCell(0, 0).color should be("b")
  }
  "generate a colored string of the form" in {
    grid.set(0,0,"b").toString should be("\n+---+\n|b 0|\n+---+\n")
    }
}
}
