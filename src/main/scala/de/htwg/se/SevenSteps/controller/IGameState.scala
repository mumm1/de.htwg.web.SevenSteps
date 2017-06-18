package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.util.Command
import scala.util.Try

trait GameState {
  def exploreCommand(com: Command, c: IController): Try[_]
}

trait IPrepare extends GameState
trait IPlay    extends GameState
trait IFinish  extends GameState

