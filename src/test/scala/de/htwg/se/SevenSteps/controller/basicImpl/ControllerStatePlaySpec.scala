package de.htwg.se.SevenSteps.controller.basicImpl

import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.model.grid.IGridFactory
import de.htwg.se.SevenSteps.util.Observer
import org.junit.runner.RunWith
import org.scalatest.Matchers.{be, _}
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerStatePlaySpec extends WordSpec {
  def before(colors: String = "aabb", cols: Int = 2, numPlayers: Int = 3): Controller = {
    val c = getController
    for (i <- 1 to numPlayers)
      c.addPlayer("Hans" + i).isSuccess should be(true)
    c.newGrid(colors, cols).isSuccess should be(true)
    c.startGame().isSuccess should be(true)
    c
  }
  def getController: Controller = {
    val injector = Guice.createInjector(new SevenStepsModule)
    new Controller(injector.getInstance(classOf[ControllerState]),
      injector.getInstance(classOf[IGridFactory]))
  }
  "A Controller in game phase play" should {
    "switch between different Players and can't undo that" in {
      val c = before()
      c.players.getCurPlayer.name should be("Hans1")
      c.nextPlayer().isSuccess should be(true)
      c.players.getCurPlayer.name should be("Hans2")
      c.nextPlayer().isSuccess should be(true)
      c.players.getCurPlayer.name should be("Hans3")
      c.nextPlayer().isSuccess should be(true)
      c.players.getCurPlayer.name should be("Hans1")
      c.undo().isSuccess should be(false)
    }
    "can only use play commands" in {
      val c = before()
      c.addPlayer("Hans").isSuccess should be(false)
      c.newGrid("ab sdd", 3).isSuccess should be(false)
      c.startGame().isSuccess should be(false)
    }
    "can restart the game" in {
      val c = before()
      c.newGame().isSuccess should be(true)
      c.gameState.isInstanceOf[Prepare] should be(true)
    }
    "allow a player to set a stone on the grid" in {
      val c = before()
      c.setStone(0, 0).isSuccess should be(true)
      c.grid.cell(0, 0).get.height should be(1)
      c.setStone(2, 0).isSuccess should be(false)
      c.setStone(0, 2).isSuccess should be(false)
      c.setStone(-1, 0).isSuccess should be(false)
      c.setStone(0, -1).isSuccess should be(false)
      c.grid.cell(0, 0).get.height should be(1)
    }
    "give on Transition Prepare -> Play the first player colors with 7 stones" in {
      val c = before()
      c.grid.getColors should be(List('a', 'b'))
      c.players.getCurPlayer.getStoneNumber should be(7)
      c.players(1).getStoneNumber should be(0)
    }
    "set the first stone everywhere on height 0" in {
      val c = before()
      c.players = c.players.setAllStonesTo(999)
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
      val c = before()
      c.players = c.players.setAllStonesTo(999)
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
      val c = before()
      c.players = c.players.setAllStonesTo(999)
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
    "only allow players to set stones on current height +0 or +1" in {
      val c = before()
      c.players = c.players.setAllStonesTo(999)
      c.setStone(0, 0).isSuccess should be(true)
      c.grid.getHeights should be(List(1, 0, 0, 0))
      c.nextPlayer().isSuccess should be(true)
      c.setStone(0, 0).isSuccess should be(false)
      c.setStone(0, 1).isSuccess should be(true)
      c.setStone(0, 0).isSuccess should be(true)
      c.setStone(1, 0).isSuccess should be(false)
      c.grid.getHeights should be(List(2, 1, 0, 0))
    }
    "only allow players to set stones when they have stones from that color" in {
      val c = before()
      c.players = c.players.setAllStonesTo(1)
      c.setStone(0, 0).isSuccess should be(true)
      c.setStone(0, 1).isSuccess should be(false)
    }
    "can check if the game is finished (there are no stones any more)" in {
      val c = before()
      c.isGameEnd should be(false)
      c.players = c.players.setAllStonesTo(0)
      while (c.bag.get().isDefined) {}
      c.isGameEnd should be(true)
    }
    "checks on command next if the game is finished" in {
      val c = before()
      c.players = c.players.setAllStonesTo(1)
      c.nextPlayer().isSuccess should be(true)
      c.setStone(0, 0).isSuccess should be(true)
      // delete all stones
      c.players = c.players.setAllStonesTo(0)
      while (c.bag.get().isDefined) {}
      c.nextPlayer().isSuccess should be(true)
      c.gameState.isInstanceOf[Finish] should be(true)
      c.getWinningPlayer should be(c.getCurPlayer)
      c.isGameEnd should be(true)
    }
    "selects the winning player with the most stones" in {
      val c = before()
      c.players = c.players.setAllStonesTo(2)
      c.setStone(0, 0).isSuccess should be(true)
      c.setStone(1, 0).isSuccess should be(true)
      val winner = c.players.getCurPlayer
      c.nextPlayer().isSuccess should be(true)
      c.setStone(1, 1).isSuccess should be(true)
      // delete all stones
      c.players = c.players.setAllStonesTo(0)
      while (c.bag.get().isDefined) {}
      c.nextPlayer().isSuccess should be(true)
      c.gameState.isInstanceOf[Finish] should be(true)
      c.getWinningPlayer.name should be(winner.name)
      c.isGameEnd should be(true)
    }
    "can check deadlock situation when grid is full" in {
      val c = before("a")
      c.players = c.players.setAllStonesTo(3)
      c.isDeadlock should be(false)
      c.setStone(0, 0).isSuccess should be(true)
      c.isDeadlock should be(true)
    }
    "can check deadlock situation when grit is not full" in {
      val c = before(colors = "ab", numPlayers = 2)
      c.players = c.players.setAllStonesTo(1)
      while (c.bag.get().isDefined) {}
      c.players = c.players.updateCurPlayer(c.getCurPlayer.incColor('b', -1))
      c.setStone(0, 0).isSuccess should be(true)
      c.nextPlayer().isSuccess should be(true)
      c.isDeadlock should be(false)
      c.players = c.players.updateCurPlayer(c.getCurPlayer.incColor('b', -1))
      c.isDeadlock should be(true)
    }
    "checks on command next if the game is in deadlock and finish it" in {
      val c = before("a")
      c.players = c.players.setAllStonesTo(3)
      c.isDeadlock should be(false)
      c.setStone(0, 0).isSuccess should be(true)
      c.isDeadlock should be(true)
      c.nextPlayer().isSuccess should be(true)
      c.gameState.isInstanceOf[Finish] should be(true)
      c.getWinningPlayer should be(c.getCurPlayer)
    }
  }
  "A Controller observed by an Observer" should {
    "notify its Observer after every Change" in {
      val c = getController
      val observer = new Observer {
        var updates: Int = 0
        override def update(): Unit = updates += 1
      }
      c.add(observer)
      c.addPlayer("Hans").isSuccess should be(true)
      observer.updates should be(1)
      c.newGrid("aaaa", 2).isSuccess should be(true)
      observer.updates should be(2)
      c.startGame().isSuccess should be(true)
      observer.updates should be(3)
      c.setStone(0, 0).isSuccess should be(true)
      observer.updates should be(4)
      c.nextPlayer().isSuccess should be(true)
      observer.updates should be(5)
    }
  }
}