package de.htwg.se.SevenSteps.model.fileIO

import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.basicImpl.Controller
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.fileIO.json.FileIO
import de.htwg.se.SevenSteps.model.grid.GridFactory
import de.htwg.se.SevenSteps.model.player.IPlayers
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FileIOSpec extends WordSpec {
  def getController: Controller = {
    val injector = Guice.createInjector(new SevenStepsModule)
    Controller(injector.getInstance(classOf[IPlayers]),
      injector.getInstance(classOf[IBag]),
      injector.getInstance(classOf[GridFactory]),
      injector.getInstance(classOf[GridFactory]).newGrid(" ", 1))
  }
  "A FileIO" should {
    "can save & restore the Controller" ignore {
      val c = getController
      val fileIO = FileIO()
      fileIO.save(c)
      val c2 = fileIO.load.get
      println(c)
      println(c2)
    }
  }
}
