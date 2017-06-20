package de.htwg.se.SevenSteps.model.fileIO.json

import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState
import de.htwg.se.SevenSteps.model.fileIO.json.CustomGenson.customGenson._
import scala.util.{Success, Try}

case class FileIO() {
  var json: String = ""
  def load: Try[ControllerState] = Success(fromJson[ControllerState](json))
  def save(cState: ControllerState): Unit = json = toJson(cState)
}
