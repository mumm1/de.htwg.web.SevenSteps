package de.htwg.se.SevenSteps.model.fileIO

import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.basicImpl.{Controller, ControllerState}
import de.htwg.se.SevenSteps.model.grid.IGridFactory
import org.junit.runner.RunWith
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
    "can save & restore the Controller" ignore {
      //      val c = getController
      //      val fileIO = FileIO()
      //      fileIO.save(c)
      //      val c2 = fileIO.load.get
      //      println(c)
      //      println(c2)
    }
  }
}
