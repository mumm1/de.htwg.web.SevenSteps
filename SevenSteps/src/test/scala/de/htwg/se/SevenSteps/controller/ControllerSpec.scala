package de.htwg.se.SevenSteps.controller

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec{

  var c = Controller()
  def before(state:GameState) {
    c = Controller()
    state match {
      case p: Prepare =>
      case p:Play    => { c.doIt(new AddPlayer("Hans")).isSuccess should be(true)
                          c.doIt(new AddPlayer("Peter")).isSuccess should be(true)
                          c.doIt(new AddPlayer("Alex")).isSuccess should be(true)
                          c.doIt(new NewGrid("aabb",2)).isSuccess should be(true)
                          c.doIt(new StartGame()).isSuccess should be(true)   }
    }}

  "A Controller in game phase prepare" should {
    "add Players and undo this" in {
      before(new Prepare(c))
      c.doIt(new AddPlayer("Hugo")).isSuccess should be(true)
      c.doIt(new AddPlayer("Peter")).isSuccess should be(true)
      c.players.length should be(2)
      c.undo().isSuccess should be(true)
      c.players.length should be(1)
    }
    "generate a new Grid and undo this" in {
      before(new Prepare(c))
      c.doIt(new NewGrid("ab sdd",3)).isSuccess should be(true)
      c.grid.cellsToString() should be("ab sdd")
      c.undo().isSuccess should be(true)
      c.grid.cellsToString() should be("")
    }
    "start the game (minimum 1 Player) and can't undo that" in {
      before(new Prepare(c))
      c.gameState.isInstanceOf[Prepare] should be(true)
      c.doIt(new StartGame()).isSuccess should be(false)
      c.gameState.isInstanceOf[Prepare] should be(true)
      c.doIt(new AddPlayer("Hugo"))
      c.doIt(new StartGame()).isSuccess should be(true)
      c.gameState.isInstanceOf[Play] should be(true)
      c.undo().isSuccess should be(false)
      c.gameState.isInstanceOf[Play] should be(true)
    }
    "do undo redo stuff" in {
      before(new Prepare(c))
      c.players.length should be(0)
      c.doIt(new AddPlayer("Hans")).isSuccess should be(true)
      c.doIt(new AddPlayer("Hans")).isSuccess should be(true)
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
      before(new Prepare(c))
      c.doIt(new AddPlayer("Hans")).isSuccess should be(true)
      c.doIt(new AddPlayer("Peter")).isSuccess should be(true)
      c.toString()
    }
    " only use prepare commands" in {
      before(new Prepare(c))
      c.doIt(new NextPlayer()).isSuccess should be(false)
      c.doIt(new SetStone(0, 0)).isSuccess should be(false)
    }
  }


  "A Controller in game phase play" should{
    "switch between different Players and can't undo that" in {
      before(new Play(c))
      c.getCurPlayer.name should be("Hans")
      c.doIt(new NextPlayer()).isSuccess should be(true)
      c.getCurPlayer.name should be("Peter")
      c.doIt(new NextPlayer()).isSuccess should be(true)
      c.getCurPlayer.name should be("Alex")
      c.doIt(new NextPlayer()).isSuccess should be(true)
      c.getCurPlayer.name should be("Hans")
      c.undo().isSuccess should be(false)

    }
    "can only use play commands" in {
      before(new Play(c))
      c.doIt(new AddPlayer("Hans")).isSuccess should be(false)
      c.doIt(new NewGrid("ab sdd", 3)).isSuccess should be(false)
      c.doIt(new StartGame()).isSuccess should be(false)
    }
    "allow a player to set a stone on the grid" in{
      before(new Play(c))
      c.doIt(new SetStone(0, 0)).isSuccess should be(true)
      c.grid.cell(0,0).height should be(1)
      c.doIt(new SetStone(2, 0)).isSuccess should be(false)
      c.doIt(new SetStone(0, 2)).isSuccess should be(false)
      c.doIt(new SetStone(-1, 0)).isSuccess should be(false)
      c.doIt(new SetStone(0, -1)).isSuccess should be(false)
      c.grid.cell(0, 0).height should be(1)
    }
    "give on Transition Prepare -> Play the Players colors with 0 stones" in {
      before(new Play(c))
      c.grid.getColors should be(List('a','b'))
      c.getCurPlayer.map.toList should be(List(('b', 0), ('a', 0)))
    }
    "set the first stone everywhere on height 0" in {
      before(new Play(c))
      c.grid.getHeights should be(List(0,0,0,0))
      c.doIt(new SetStone(0, 0)).isSuccess should be(true)
      c.grid.getHeights should be(List(1, 0, 0, 0));
      c.undo().isSuccess should be(true)

      c.doIt(new SetStone(0, 1)).isSuccess should be(true)
      c.grid.getHeights should be(List(0, 1, 0, 0));
      c.undo().isSuccess should be(true)

      c.doIt(new SetStone(1, 0)).isSuccess should be(true)
      c.grid.getHeights should be(List(0, 0, 1, 0));
      c.undo().isSuccess should be(true)

      c.doIt(new SetStone(1, 1)).isSuccess should be(true)
      c.grid.getHeights should be(List(0, 0, 0, 1));
    }
    "set the second Stone neighboring to the first stone" in {
      before(new Play(c))
      c.doIt(new SetStone(0, 0)).isSuccess should be(true)
      c.doIt(new SetStone(0, 1)).isSuccess should be(true)
      c.grid.getHeights should be(List(1, 1, 0, 0))
      c.undo().isSuccess should be(true)

      c.doIt(new SetStone(1, 0)).isSuccess should be(true)
      c.grid.getHeights should be(List(1, 0, 1, 0))
      c.undo().isSuccess should be(true)

      c.doIt(new SetStone(1, 1)).isSuccess should be(false)
      c.grid.getHeights should be(List(1, 0, 0, 0))
    }
    "not set in one turn a grid cell twice" in {
      before(new Play(c))
      c.doIt(new SetStone(0, 0)).isSuccess should be(true)
      c.doIt(new SetStone(0, 1)).isSuccess should be(true)
      c.doIt(new SetStone(1, 1)).isSuccess should be(true)
      c.doIt(new SetStone(1, 0)).isSuccess should be(true)
      c.grid.getHeights should be(List(1,1,1,1))
      c.doIt(new SetStone(0, 0)).isSuccess should be(false)
      c.doIt(new SetStone(0, 1)).isSuccess should be(false)
      c.doIt(new SetStone(1, 0)).isSuccess should be(false)
      c.doIt(new SetStone(1, 1)).isSuccess should be(false)
      c.grid.getHeights should be(List(1,1,1,1))

      c.undo().isSuccess should be(true)
      c.grid.getHeights should be(List(1, 1, 0, 1))
      c.doIt(new SetStone(1, 0)).isSuccess should be(true)
    }
  }


}