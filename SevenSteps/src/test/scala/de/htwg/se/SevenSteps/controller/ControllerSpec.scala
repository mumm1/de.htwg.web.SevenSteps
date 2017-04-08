package de.htwg.se.SevenSteps.controller

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

import de.htwg.se.SevenSteps.model._

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec{
  
  "A Controller in game phase prepare" should{
    var controller = Controller(new Grid("aabbcc",2))
  "add a Players" in {
      controller.addPlayer(new Player("Hugo"))
      controller.addPlayer(new Player("Tom"))
      "print them" in {
        controller.toString() should be("")
      }
    }
    
  }
}
