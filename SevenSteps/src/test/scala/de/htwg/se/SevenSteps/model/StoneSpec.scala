package de.htwg.se.SevenSteps.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StoneSpec extends WordSpec{
  "A Stone" should{
    val stone = Stone("a")
    
  "have a color" in {
    stone.color should be("a")
  }
}
}
