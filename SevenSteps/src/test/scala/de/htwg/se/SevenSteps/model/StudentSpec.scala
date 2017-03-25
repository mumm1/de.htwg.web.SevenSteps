package de.htwg.se.SevenSteps.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StudentSpec extends FlatSpec with Matchers {
  "A Student" should "have a name" in {
    Student("Your Name").name should be("Your Name")
  }
}
