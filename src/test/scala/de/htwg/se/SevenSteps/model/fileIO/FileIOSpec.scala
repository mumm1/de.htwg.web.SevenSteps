package de.htwg.se.SevenSteps.model.fileIO

import de.htwg.se.SevenSteps.controller.basicImpl.Controller
import de.htwg.se.SevenSteps.model.fileIO.json.FileIO
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FileIOSpec extends WordSpec {
  "A FileIO" should {
    "can save & restore the Controller" in {
      val c = new Controller()
      val fileIO = FileIO()
      fileIO.save(c)
      val c2 = fileIO.load.get
      println(c)
      println(c2)
    }
  }
}
