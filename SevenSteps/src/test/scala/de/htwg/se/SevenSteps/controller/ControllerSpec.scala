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
      var c = Controller()
      c.exploreCommand(new AddPlayer("Hugo")).isSuccess should be(true)
      c.exploreCommand(new AddPlayer("Peter")).isSuccess should be(true)
      c.players.length should be(2)
      c.undo()
      c.players.length should be(1)
    }
    "generate a new Grid and undo this" in {
      var c = Controller()
      c.exploreCommand(new NewGrid("ab sdd",3)).isSuccess should be(true)
      c.grid.cellsToString() should be("ab sdd")
      c.undo()
      c.grid.cellsToString() should be(" ")
    }
    "start the game (minimum 1 Player) and can't undo that" in {
      var c = Controller()
      c.gameState.isInstanceOf[Prepare] should be(true)
      c.exploreCommand(new StartGame()).isSuccess should be(false)
      c.gameState.isInstanceOf[Prepare] should be(true)
      c.exploreCommand(new AddPlayer("Hugo"))
      c.exploreCommand(new StartGame()).isSuccess should be(true)
      c.gameState.isInstanceOf[Play] should be(true)      
      c.undo()
      c.gameState.isInstanceOf[Play] should be(true)
    }
    "do undo redo stuff" in {
      var c = Controller()
      c.players.length should be(0)  
      c.exploreCommand(new AddPlayer("Hans")).isSuccess should be(true)
      c.exploreCommand(new AddPlayer("Hans")).isSuccess should be(true)
      c.players.length should be(2)      
      c.undo()
      c.undo()
      c.players.length should be(0)
      c.undo()
      c.players.length should be(0)
      c.redo()
      c.redo()
      c.players.length should be(2)  
      c.redo()
    }
  }
  
  
}
