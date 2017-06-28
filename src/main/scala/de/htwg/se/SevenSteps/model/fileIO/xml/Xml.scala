package de.htwg.se.SevenSteps.model.fileIO.xml

import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState
import de.htwg.se.SevenSteps.model.fileIO.IFileIO

case class Xml() extends IFileIO {
  var help: ControllerState = null
  def save(cState: ControllerState): Unit = help = cState
  def load: ControllerState = help
}
