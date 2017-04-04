package de.htwg.se.SevenSteps.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StoneSpec extends WordSpec{
  "A Student" should{
    val student = Student("Your Name")
    
  "have a name" in {
    student.name should be("Your Name")
  }
  "can increment a Number" in {
    student.f(2) should be(3)
  }
}
}
