package de.htwg.se.SevenSteps.controller.basicImpl

import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.model.fileIO.IFileIO
import de.htwg.se.SevenSteps.model.grid.IGridFactory
import de.htwg.se.SevenSteps.model.grid.basicImpl.Grid
import de.htwg.se.SevenSteps.model.player.basicImpl.Players
import de.htwg.se.SevenSteps.util.{Observer, UndoManager}
import org.junit.runner.RunWith
import org.scalatest.Matchers.{be, _}
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerStatePrepareSpec extends WordSpec {
  def getController: Controller = {
    val injector = Guice.createInjector(new SevenStepsModule)
    new Controller(injector.getInstance(classOf[ControllerState]),
      injector.getInstance(classOf[IGridFactory]),
      injector.getInstance(classOf[IFileIO]))
  }
  "A Controller" should {
    "can save and load" in {
      val c = getController
      val oldState = c.state.copy()
      c.save()
      c.addPlayer("hans").get.newGrid("aabb", 2).get.startGame()
      c.load()
      oldState.message = "Load: " + oldState.message
      c.state should be(oldState)
      c.undoManager should be(UndoManager())
    }
  }

  "A Controller in game phase prepare" should {
    "have default values" in {
      val c = getController
      c.state.gameState.isInstanceOf[Prepare] should be(true)
      c.state.message should be("Welcome to SevenSteps")
    }
    "add Players and undo this" in {
      val c = getController
      c.addPlayer("Hugo").isSuccess should be(true)
      c.addPlayer("Peter").isSuccess should be(true)
      c.state.players should be(new Players().push("Hugo").push("Peter"))
      c.undo().get.state.players should be(new Players().push("Hugo"))
    }
    "set color of grid and undo this" in {
      val c = getController
      c.newGrid("aabb",2).isSuccess should be(true)
      c.setColor(0, 0, 'b').get.state.grid should be(new Grid("babb", 2))
      c.undo().get.state.grid should be(new Grid("aabb", 2))
    }
    "set color only inside of the grid" in {
      val c = getController
      c.newGrid("a",1).isSuccess should be(true)
      c.setColor(0,0,'b').isSuccess should be(true)
      c.setColor(-1,0,'b').isSuccess should be(false)
      c.setColor(1,0,'b').isSuccess should be(false)
    }
    "generate a new Grid and undo this" in {
      val c = getController
      c.newGrid("z", 1).isSuccess should be(true)
      c.newGrid("ab sdd", 3).isSuccess should be(true)
      c.state.grid.cellsToString() should be("ab sdd")
      c.undo().isSuccess should be(true)
      c.state.grid.cellsToString() should be("z")
    }
    "start the game (minimum 1 Player and a non empty grid) and can't undo that" in {
      val c = getController
      c.state.gameState.isInstanceOf[Prepare] should be(true)
      c.startGame().isSuccess should be(false)
      c.state.gameState.isInstanceOf[Prepare] should be(true)
      c.addPlayer("Hugo")
      c.startGame().isSuccess should be(false)
      c.newGrid("aabb", 2).isSuccess should be(true)
      c.startGame().isSuccess should be(true)
      c.state.gameState.isInstanceOf[Play] should be(true)
      c.undo().isSuccess should be(false)
      c.state.gameState.isInstanceOf[Play] should be(true)
    }
    "do undo redo stuff" in {
      val c = getController
      c.state.players.length should be(0)
      c.addPlayer("Hans").isSuccess should be(true)
      c.addPlayer("Hans").isSuccess should be(true)
      c.state.players.length should be(2)
      c.undo().isSuccess should be(true)
      c.undo().isSuccess should be(true)
      c.state.players.length should be(0)
      c.undo().isSuccess should be(false)
      c.state.players.length should be(0)
      c.redo().isSuccess should be(true)
      c.redo().isSuccess should be(true)
      c.state.players.length should be(2)
      c.redo().isSuccess should be(false)
    }
    "generate a cool String" in {
      val c = getController
      c.addPlayer("Hans").isSuccess should be(true)
      c.addPlayer("Peter").isSuccess should be(true)
      c.state.toString()
    }
    " only use prepare commands" in {
      val c = getController
      c.nextPlayer().isSuccess should be(false)
      c.setStone(0, 0).isSuccess should be(false)
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