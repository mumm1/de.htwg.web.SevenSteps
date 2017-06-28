package de.htwg.se.SevenSteps.model.fileIO
import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState

trait IFileIO {
  def save(cState: ControllerState): Unit
  def load: ControllerState
}

