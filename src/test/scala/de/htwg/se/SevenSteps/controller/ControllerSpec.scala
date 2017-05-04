package de.htwg.se.SevenSteps.controller

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec {
  var c = Controller()
  def before(state: GameState): Unit = {
    c = Controller()
    state match {
      case _: Prepare =>
      case _: Play =>
        c.addPlayer("Hans").isSuccess should be(true)
        c.addPlayer("Peter").isSuccess should be(true)
        c.addPlayer("Alex").isSuccess should be(true)
        c.newGrid("aabb", 2).isSuccess should be(true)
        c.startGame().isSuccess should be(true)
    }
  }
  "A Controller in game phase prepare" should {
    "add Players and undo this" in {
      before(Prepare(c))
      c.addPlayer("Hugo").isSuccess should be(true)
      c.addPlayer("Peter").isSuccess should be(true)
      c.players.length should be(2)
      c.undo().isSuccess should be(true)
      c.players.length should be(1)
    }
    "generate a new Grid and undo this" in {
      before(Prepare(c))
      c.newGrid("ab sdd", 3).isSuccess should be(true)
      c.grid.cellsToString() should be("ab sdd")
      c.undo().isSuccess should be(true)
      c.grid.cellsToString() should be("")
    }
    "start the game (minimum 1 Player and a non empty grid) and can't undo that" in {
      before(Prepare(c))
      c.gameState.isInstanceOf[Prepare] should be(true)
      c.startGame().isSuccess should be(false)
      c.gameState.isInstanceOf[Prepare] should be(true)
      c.addPlayer("Hugo")
      c.startGame().isSuccess should be(false)
      c.newGrid("aabb", 2).isSuccess should be(true)
      c.startGame().isSuccess should be(true)
      c.gameState.isInstanceOf[Play] should be(true)
      c.undo().isSuccess should be(false)
      c.gameState.isInstanceOf[Play] should be(true)
    }
    "do undo redo stuff" in {
      before(Prepare(c))
      c.players.length should be(0)
      c.addPlayer("Hans").isSuccess should be(true)
      c.addPlayer("Hans").isSuccess should be(true)
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
      before(Prepare(c))
      c.addPlayer("Hans").isSuccess should be(true)
      c.addPlayer("Peter").isSuccess should be(true)
      c.toString()
    }
    " only use prepare commands" in {
      before(Prepare(c))
      c.nextPlayer().isSuccess should be(false)
      c.setStone(0, 0).isSuccess should be(false)
    }
  }
  "A Controller in game phase play" should {
    "switch between different Players and can't undo that" in {
      before(Play(c))
      c.getCurPlayer.name should be("Hans")
      c.nextPlayer().isSuccess should be(true)
      c.getCurPlayer.name should be("Peter")
      c.nextPlayer().isSuccess should be(true)
      c.getCurPlayer.name should be("Alex")
      c.nextPlayer().isSuccess should be(true)
      c.getCurPlayer.name should be("Hans")
      c.undo().isSuccess should be(false)
    }
    "can only use play commands" in {
      before(Play(c))
      c.addPlayer("Hans").isSuccess should be(false)
      c.newGrid("ab sdd", 3).isSuccess should be(false)
      c.startGame().isSuccess should be(false)
    }
    "allow a player to set a stone on the grid" in {
      before(Play(c))
      c.setStone(0, 0).isSuccess should be(true)
      c.grid.cell(0, 0).get.height should be(1)
      c.setStone(2, 0).isSuccess should be(false)
      c.setStone(0, 2).isSuccess should be(false)
      c.setStone(-1, 0).isSuccess should be(false)
      c.setStone(0, -1).isSuccess should be(false)
      c.grid.cell(0, 0).get.height should be(1)
    }
    "give on Transition Prepare -> Play the first player colors with 7 stones" in {
      before(Play(c))
      c.grid.getColors should be(List('a', 'b'))
      c.players.getCurPlayer.getStoneNumber should be(7)
      c.players(1).getStoneNumber should be(0)
    }
    "set the first stone everywhere on height 0" in {
      before(Play(c))
      c.players=c.players.setAllStonesTo(999);
      c.grid.getHeights should be(List(0, 0, 0, 0))
      c.setStone(0, 0).isSuccess should be(true)
      c.grid.getHeights should be(List(1, 0, 0, 0))
      c.undo().isSuccess should be(true)
      c.setStone(0, 1).isSuccess should be(true)
      c.grid.getHeights should be(List(0, 1, 0, 0))
      c.undo().isSuccess should be(true)
      c.setStone(1, 0).isSuccess should be(true)
      c.grid.getHeights should be(List(0, 0, 1, 0))
      c.undo().isSuccess should be(true)
      c.setStone(1, 1).isSuccess should be(true)
      c.grid.getHeights should be(List(0, 0, 0, 1))
    }
    "set the second Stone neighboring to the first stone" in {
      before(Play(c))
      c.players=c.players.setAllStonesTo(999);
      c.setStone(0, 0).isSuccess should be(true)
      c.setStone(0, 1).isSuccess should be(true)
      c.grid.getHeights should be(List(1, 1, 0, 0))
      c.undo().isSuccess should be(true)
      c.setStone(1, 0).isSuccess should be(true)
      c.grid.getHeights should be(List(1, 0, 1, 0))
      c.undo().isSuccess should be(true)
      c.setStone(1, 1).isSuccess should be(false)
      c.grid.getHeights should be(List(1, 0, 0, 0))
    }
    "not set in one turn a grid cell twice" in {
      before(Play(c))
      c.players=c.players.setAllStonesTo(999);
      c.players = c.players.setAllStonesTo(99)
      c.setStone(0, 0).isSuccess should be(true)
      c.setStone(0, 1).isSuccess should be(true)
      c.setStone(1, 1).isSuccess should be(true)
      c.setStone(1, 0).isSuccess should be(true)
      c.grid.getHeights should be(List(1, 1, 1, 1))
      c.setStone(0, 0).isSuccess should be(false)
      c.setStone(0, 1).isSuccess should be(false)
      c.setStone(1, 0).isSuccess should be(false)
      c.setStone(1, 1).isSuccess should be(false)
      c.grid.getHeights should be(List(1, 1, 1, 1))
      c.undo().isSuccess should be(true)
      c.grid.getHeights should be(List(1, 1, 0, 1))
      c.setStone(1, 0).isSuccess should be(true)
    }
  }
}