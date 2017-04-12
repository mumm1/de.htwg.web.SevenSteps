package de.htwg.se.SevenSteps.controller

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

import de.htwg.se.SevenSteps.model._

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec{
  
  "A Controller in game phase prepare" should{
    var controller = Controller()
    "add Players and undo this" in {
      controller.exploreCommand(new AddPlayer("Hans"))
      controller.exploreCommand(new AddPlayer("Peter"))
      controller.players.length should be(2)
      controller.undo()
      controller.players.length should be(1)
    }
    "generate a new Grid" in {
      controller.exploreCommand(new NewGrid("ab sdd",3))
      controller.grid.cellsToString() should be("ab sdd")
    }
  }
  
  
}
