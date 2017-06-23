package de.htwg.se.SevenSteps.model.fileIO

import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.controller.basicImpl.{Controller, ControllerState}
import de.htwg.se.SevenSteps.model.grid.IGridFactory
import org.junit.runner.RunWith
import org.scalactic.TolerantNumerics
import org.scalatest.Matchers.{be, _}
import org.scalatest._
import org.scalatest.junit.JUnitRunner

trait IOBehaviors {
  this: WordSpec =>
  def saveAndLoad(fileIO: IFileIO, c: IController): Unit = {
    "can save & restore the default ControllerState" in {
      fileIO.save(c.state)
      fileIO.load should be(c.state)
    }
    "can save & restore a complex ControllerState" in {
      implicit val doubleEquality = TolerantNumerics.tolerantDoubleEquality(0.01)
      c
        .addPlayer("Hans").get.addPlayer("Alex").get
        .newGrid("aabb", 2).get.startGame().get
      fileIO.save(c.state)
      val c2 = fileIO.load
      c2.players should be(c.state.players)
      c2.bag should be(c.state.bag)
      c2.grid should be(c.state.grid)
      fileIO.load should be(c.state)
    }
  }
}

@RunWith(classOf[JUnitRunner])
class FileIOSpec extends WordSpec with IOBehaviors {
  def getController: Controller = {
    val injector = Guice.createInjector(new SevenStepsModule)
    new Controller(injector.getInstance(classOf[ControllerState]),
      injector.getInstance(classOf[IGridFactory]))
  }
  "A json FileIO" should {
    behave like saveAndLoad(json.FileIO(), getController)
  }
  "A xml  FileIO" should {
    behave like saveAndLoad(xml.FileIO(), getController)
  }
}
