package de.htwg.se.SevenSteps.model

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec {
  "A new Grid with 1 empty Cell" should {
    val grid = Grid(1, 1)
    "have the right dimensions" in {
      grid.cols should be(1)
      grid.rows should be(1)
    }
    "generate a string of the form" in {
      grid.toString should be("\n+---+\n|   |\n+---+\n")
    }
    "set a color to the cell" in {
      grid.set(0, 0, 'b').cell(0, 0).get.color should be('b')
    }
    "generate a colored string of the form" in {
      grid.set(0, 0, 'b').set(0, 0, 3).toString should be("\n+---+\n|b 3|\n+---+\n")
    }
  }
  "A Grid" should {
    "be possible to create empty" in {
      Grid(3, 2)
      Grid(10, 40)
    }
    "be possible to create colored" in {
      new Grid(colors = "abc", cols = 2).toString() should be("\n+---+---+\n|a 0|b 0|\n+---+---+\n|c 0|   |\n+---+---+\n")
      new Grid(colors = "abc  abc", cols = 3)
    }
  }
  "A colored Grid" should {
    val grid = new Grid("abcab abcddd", 3)
    "have the right color" in {
      grid.cellsToString() should be("abcab abcddd")
    }
  }
  "A colored Grid" should {
    val grid = new Grid("ab  cd", 2)
    "get the cells " in {
      grid.cell(0, 0).get.color should be('a')
      grid.cell(0, 1).get.color should be('b')
      grid.cell(1, 0).get.color should be(' ')
      grid.cell(1, 1).get.color should be(' ')
      grid.cell(2, 0).get.color should be('c')
      grid.cell(2, 1).get.color should be('d')
      grid.cell(-1, 0).isFailure should be(true)
      grid.cell(4, 0).isFailure should be(true)
      grid.cell(0, 3).isFailure should be(true)
    }
    "generate a List of colors without the white space" in {
      grid.getColors should be(List('a', 'b', 'c', 'd'))
    }
    "generate a List of heights" in {
      grid.set(0, 0, 0).set(0, 1, 1).set(1, 0, 2).set(1, 1, 3).set(2, 0, 4).set(2, 1, 5).getHeights should be(List(0, 1, 2, 3, 4, 5))
    }
  }
}
