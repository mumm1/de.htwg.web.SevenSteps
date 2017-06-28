package de.htwg.se.SevenSteps.model.fileIO.json

import de.htwg.se.SevenSteps.model.fileIO.FileIOSpec
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class JsonSpec extends WordSpec with FileIOSpec {
  "A json FileIO" should {
    behave like saveAndLoad(Json())
  }
}
