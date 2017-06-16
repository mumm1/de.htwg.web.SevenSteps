package de.htwg.se.SevenSteps.model.fileIO

import de.htwg.se.SevenSteps.controller.IController
import scala.util.Try

trait FileIO {
  def load: Try[IController]
  def save(c: IController): Unit
}
