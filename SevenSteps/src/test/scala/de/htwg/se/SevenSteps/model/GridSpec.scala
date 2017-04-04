package de.htwg.se.SevenSteps.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec{
  "A Grid" should{
    val grid = Grid(3,2)
    
  "have the right dimensions" in {
    grid.cols should be(2)
    grid.rows should be(3)
  }
}
}
