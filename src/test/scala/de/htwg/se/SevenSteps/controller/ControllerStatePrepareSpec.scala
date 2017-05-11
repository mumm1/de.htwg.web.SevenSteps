package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model.Players
import de.htwg.se.SevenSteps.util.Observer
import org.junit.runner.RunWith
import org.scalatest.Matchers.{be, _}
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerStatePrepareSpec extends WordSpec {
  var c = Controller()
  "A Controller in game phase prepare" should {
    "add Players and undo this" in {
      c = Controller()
      c.addPlayer("Hugo").isSuccess should be(true)
      c.addPlayer("Peter").get should be(c.copy(players = Players().push("Hugo").push("Peter"), message = "Added Player Peter"))
      c.undo().get should be(c.copy(players = Players().push("Hugo"), message = "Deleted Player Peter"))
    }
    "generate a new Grid and undo this" in {
      c = Controller()
      c.newGrid("ab sdd", 3).isSuccess should be(true)
      c.grid.cellsToString() should be("ab sdd")
      c.undo().isSuccess should be(true)
      c.grid.cellsToString() should be("")
    }
    "start the game (minimum 1 Player and a non empty grid) and can't undo that" in {
      c = Controller()
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
      c = Controller()
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
      c = Controller()
      c.addPlayer("Hans").isSuccess should be(true)
      c.addPlayer("Peter").isSuccess should be(true)
      c.toString()
    }
    " only use prepare commands" in {
      c = Controller()
      c.nextPlayer().isSuccess should be(false)
      c.setStone(0, 0).isSuccess should be(false)
    }
  }
  "A Controller observed by an Observer" should {
    "notify its Observer after every Change" in {
      c = Controller()
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