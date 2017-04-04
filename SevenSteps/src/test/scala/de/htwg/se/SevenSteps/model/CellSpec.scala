package de.htwg.se.SevenSteps.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CellSpec extends WordSpec{
  "A Cell" should{
    val student = Cell("a",2)
    
  "have a color" in {
    student.color should be("a")
  }
  "have a height" in {
    student.height should be(2)
  }
}
}
