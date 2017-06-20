package de.htwg.se.SevenSteps.model.fileIO

import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.basicImpl.{Controller, ControllerState}
import de.htwg.se.SevenSteps.model.grid.IGridFactory
import org.junit.runner.RunWith
import org.scalactic.TolerantNumerics
import org.scalatest.Matchers.{be, _}
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FileIOSpec extends WordSpec {
  def getController: Controller = {
    val injector = Guice.createInjector(new SevenStepsModule)
    new Controller(injector.getInstance(classOf[ControllerState]),
      injector.getInstance(classOf[IGridFactory]))
  }
  "A FileIO" should {
    val fileIO = json.FileIO()
    "can save & restore the default ControllerState" in {
      val c = getController
      fileIO.save(c.c)
      fileIO.load should be(c.c)
    }
    "can save & restore a complex ControllerState" in {
      implicit val doubleEquality = TolerantNumerics.tolerantDoubleEquality(0.01)
      val c = getController
      c
        .addPlayer("Hans").get.addPlayer("Alex").get
        .newGrid("aabb", 2).get.startGame().get
      fileIO.save(c.c)
      val c2 = fileIO.load
      c2.players should be(c.c.players)
      c2.bag should be(c.c.bag)
      c2.grid should be(c.c.grid)
      fileIO.load should be(c.c)
    }
    "Double" in {
      val x: Double = 1.0
      x should be(1.0)
    }
  }
}