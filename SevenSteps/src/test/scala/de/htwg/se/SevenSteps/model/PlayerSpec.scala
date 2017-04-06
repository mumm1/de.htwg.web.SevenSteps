package de.htwg.se.SevenSteps.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec {
  
  "A Player" should {
    
    "have a name" in {
      Player("Julius", 50).name should be("Julius")
    }
    "have points" in {
      Player ("Julius", 50).points should be (50)
    }
  }
  
  
  
  
  
  
}