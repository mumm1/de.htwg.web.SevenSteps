package de.htwg.se.SevenSteps.model.fileIO.xml

import de.htwg.se.SevenSteps.model.fileIO.FileIOSpec
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class XmlSpec extends WordSpec with FileIOSpec {
  "A xml FileIO" should {
    behave like saveAndLoad(Xml())
  }
}
