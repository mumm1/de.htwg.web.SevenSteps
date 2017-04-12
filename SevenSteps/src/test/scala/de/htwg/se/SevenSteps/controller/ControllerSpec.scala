package de.htwg.se.SevenSteps.controller

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

import de.htwg.se.SevenSteps.model._

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec{
  
  "A Controller in game phase prepare" should{   
    "add Players and undo this" in {
      var controller = Controller()
      controller.exploreCommand(new AddPlayer("Hugo"))
      controller.exploreCommand(new AddPlayer("Peter"))
      controller.players.length should be(2)
      controller.undo()
      controller.players.length should be(1)
    }
    "generate a new Grid and undo this" in {
      var controller = Controller()
      controller.exploreCommand(new NewGrid("ab sdd",3))
      controller.grid.cellsToString() should be("ab sdd")
      controller.undo()
      controller.grid.cellsToString() should be(" ")
    }
    "start the game (minimum 1 Player) and can't undo that" in {
      var controller = Controller()
      controller.gameState.isInstanceOf[Prepare] should be(true)
      controller.exploreCommand(new StartGame())
      controller.gameState.isInstanceOf[Prepare] should be(true)
      controller.exploreCommand(new AddPlayer("Hugo"))
      controller.exploreCommand(new StartGame())
      controller.gameState.isInstanceOf[Play] should be(true)      
      controller.undo()
      controller.gameState.isInstanceOf[Play] should be(true)
    }
    "do undo redo stuff" in {
      var controller = Controller()
      println(controller.toString())
      controller.players.length should be(0)  
      controller.exploreCommand(new AddPlayer("Hans"))
      controller.exploreCommand(new AddPlayer("Hans"))
      controller.players.length should be(2)      
      controller.undo()
      controller.undo()
      controller.players.length should be(0)
      controller.undo()
      controller.players.length should be(0)
      controller.redo()
      controller.redo()
      controller.players.length should be(2)  
      
    }
  }
  
  
}
