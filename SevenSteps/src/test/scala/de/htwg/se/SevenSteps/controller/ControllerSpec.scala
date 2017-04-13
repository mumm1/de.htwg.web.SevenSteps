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
      c.undo().isSuccess should be(true)
      c.players.length should be(1)
    }
    "generate a new Grid and undo this" in {
      var c = Controller()
      c.exploreCommand(new NewGrid("ab sdd",3)).isSuccess should be(true)
      c.grid.cellsToString() should be("ab sdd")
      c.undo().isSuccess should be(true)
      c.grid.cellsToString() should be("")
    }
    "start the game (minimum 1 Player) and can't undo that" in {
      var c = Controller()
      c.gameState.isInstanceOf[Prepare] should be(true)
      c.exploreCommand(new StartGame()).isSuccess should be(false)
      c.gameState.isInstanceOf[Prepare] should be(true)
      c.exploreCommand(new AddPlayer("Hugo"))
      c.exploreCommand(new StartGame()).isSuccess should be(true)
      c.gameState.isInstanceOf[Play] should be(true)      
      c.undo().isSuccess should be(false)
      c.gameState.isInstanceOf[Play] should be(true)
    }
    "do undo redo stuff" in {
      var c = Controller()
      c.players.length should be(0)  
      c.exploreCommand(new AddPlayer("Hans")).isSuccess should be(true)
      c.exploreCommand(new AddPlayer("Hans")).isSuccess should be(true)
      c.players.length should be(2)      
      c.undo().isSuccess should be(true)
      c.undo().isSuccess should be(true)
      c.players.length should be(0)
      c.undo().isSuccess should be(false)
      c.players.length should be(0)
      c.redo().isSuccess should be(true)
      c.redo().isSuccess should be(true)
      c.players.length should be(2)  
      c.redo().isSuccess should be(false)
    }
    "generate a cool String" in {
      var c = Controller()
      c.exploreCommand(new AddPlayer("Hans")).isSuccess should be(true)
      c.exploreCommand(new AddPlayer("Peter")).isSuccess should be(true)
      c.toString()
    }
    "can only use prepare commands" in {
      var c = Controller()
      c.exploreCommand(new NextPlayer()).isSuccess should be(false)
      c.exploreCommand(new SetStonde(0,0)).isSuccess should be(false)
    }
  }
  "A Controller in game phase play" should{
    "switch between different Players and can't undo that" in {
      var c = Controller() 
      c.exploreCommand(new AddPlayer("Hans")).isSuccess should be(true)
      c.exploreCommand(new AddPlayer("Peter")).isSuccess should be(true)
      c.exploreCommand(new AddPlayer("Alex")).isSuccess should be(true)
      c.exploreCommand(new StartGame()).isSuccess should be(true)
      c.getCurPlayer().name should be("Hans")
      c.exploreCommand(new NextPlayer()).isSuccess should be(true)   
      c.getCurPlayer().name should be("Peter")      
      c.exploreCommand(new NextPlayer()).isSuccess should be(true)   
      c.getCurPlayer().name should be("Alex")  
      c.exploreCommand(new NextPlayer()).isSuccess should be(true)   
      c.getCurPlayer().name should be("Hans") 
      c.undo().isSuccess should be(false)
      
    }
    "can only use play commands" in {
      var c = Controller()
      c.gameState=Play(c)
      c.exploreCommand(new AddPlayer("Hans")).isSuccess should be(false)   
      c.exploreCommand(new NewGrid("ab sdd",3)).isSuccess should be(false) 
      c.exploreCommand(new StartGame()).isSuccess should be(false)
    }
    "allow a player to set a stone on the grid" in{
      var c = Controller() 
      c.exploreCommand(new AddPlayer("Hans")).isSuccess should be(true)
      c.exploreCommand(new NewGrid("a",1)).isSuccess should be(true)
      c.exploreCommand(new StartGame()).isSuccess should be(true)
      
      c.exploreCommand(new SetStonde( 0, 0)).isSuccess should be(true)
      c.grid.cell(0,0).height should be(1)
      c.exploreCommand(new SetStonde( 1, 0)).isSuccess should be(false)
      c.exploreCommand(new SetStonde( 0, 1)).isSuccess should be(false)
      c.exploreCommand(new SetStonde(-1, 0)).isSuccess should be(false)
      c.exploreCommand(new SetStonde( 0,-1)).isSuccess should be(false)
      c.grid.cell(0,0).height should be(1)      
      
    }
  }
  
  
}
