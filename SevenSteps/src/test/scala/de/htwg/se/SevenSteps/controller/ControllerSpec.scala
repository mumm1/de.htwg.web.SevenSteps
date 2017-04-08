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
    "add Players and print the info" in {
      controller.addPlayer(new Player("Hugo"))
      controller.addPlayer(new Player("Tom"))
      controller.players.length should be(2)
      controller.toString()
    }
    "color the grid" in {
      controller.color(0,1,'z')
      controller.grid.cell(0, 1).color should be('z')
    }
  "A Controller in game phase play" should{
    var controller = Controller(new Grid("aabbcc",2))
    controller.addPlayer(new Player("Hugo"))
    controller.addPlayer(new Player("Tom"))
    "set a stone" in {
      
    }
  }
  }
}
