package de.htwg.se.SevenSteps.model.fileIO

import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.basicImpl.Controller
import org.scalatest.Matchers._
import org.scalatest._

trait FileIOSpec {
  this: WordSpec =>
  def saveAndLoad(fileIO: IFileIO): Unit = {
    "save & restore the default ControllerState" in {
      val c = getController
      fileIO.save(c.state)
      fileIO.load should be(c.state)
    }
    "save & restore a complex ControllerState" in {
      val c = getController
      c.addPlayer("Hans").get.addPlayer("Alex").get
        .newGrid("aabb", 2).get.startGame().get
      fileIO.save(c.state)
      fileIO.load should be(c.state)
    }
  }
  def getController: Controller = {
    val injector = Guice.createInjector(new SevenStepsModule)
    injector.getInstance(classOf[Controller])
  }
}