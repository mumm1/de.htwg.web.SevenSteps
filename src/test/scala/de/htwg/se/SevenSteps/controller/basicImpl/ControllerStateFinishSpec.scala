package de.htwg.se.SevenSteps.controller.basicImpl

import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.model.fileIO.IFileIO
import de.htwg.se.SevenSteps.model.grid.IGridFactory
import de.htwg.se.SevenSteps.model.grid.basicImpl.Grid
import de.htwg.se.SevenSteps.model.player.basicImpl.{Player, Players}
import org.junit.runner.RunWith
import org.scalatest.Matchers.{be, _}
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerStateFinishSpec extends WordSpec {
  def before(colors: String = "aabb", cols: Int = 2, numPlayers: Int = 3): Controller = {
    val injector = Guice.createInjector(new SevenStepsModule)
    val c = new Controller(injector.getInstance(classOf[ControllerState]),
      injector.getInstance(classOf[IGridFactory]),
      injector.getInstance(classOf[IFileIO]))
    for (i <- 1 to numPlayers)
      c.addPlayer("Hans" + i).isSuccess should be(true)
    c.newGrid(colors, cols).isSuccess should be(true)
    c.startGame().isSuccess should be(true)
    c.state.gameState = Finish()
    c
  }
  "A Controller in game phase finish" should {
    "can only use newGame Command and can't undo that" in {
      val c=before()
      c.nextPlayer().isSuccess should be(false)
      c.newGrid(" ", 1).isSuccess should be(false)
      c.startGame().isSuccess should be(false)
      c.setColor(0, 0, 'z').isSuccess should be(false)
      c.setStone(0, 0).isSuccess should be(false)
      c.newGame().isSuccess should be(true)
      c.undo().isSuccess should be(false)
    }
    "on command newGame go into state Prepare" in{
      val c=before()
      c.newGame().isSuccess should be(true)
      c.state.gameState.isInstanceOf[Prepare] should be(true)
    }
    "reset on command newGame all Points from Players and Heights of the grid" in{
      val c=before()
      c.state.players = new Players().push(Player("Hans", 10, None))
      c.state.grid = new Grid("a", 1).set(0, 0, 5)
      c.newGame().isSuccess should be(true)
      c.state.players should be(new Players().push("Hans"))
      c.state.grid should be(new Grid("a", 1))
    }
  }
}