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
}
}
