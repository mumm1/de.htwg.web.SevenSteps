package de.htwg.se.SevenSteps.model.fileIO.json

import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.fileIO.IFileIO
import de.htwg.se.SevenSteps.model.fileIO.json.CustomGenson.customGenson._
import scala.util.{Success, Try}

case class FileIO() extends IFileIO {
  var json: String = ""
  override def load: Try[IController] = {
    Success(fromJson[IController](json))
  }
  override def save(c: IController): Unit = {
    json = toJson(c)
  }
}
