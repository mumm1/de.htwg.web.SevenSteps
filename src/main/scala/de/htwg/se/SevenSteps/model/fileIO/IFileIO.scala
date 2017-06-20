package de.htwg.se.SevenSteps.model.fileIO

import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState

import scala.util.Try

trait IFileIO {
  def load: Try[ControllerState]
  def save(cState: ControllerState): Unit
}
