package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.controller.controllerBasicImpl._
import de.htwg.se.SevenSteps.util.Command

import scala.util.{Failure, Try}

trait GameState {
  def exploreCommand(com: Command): Try[_]
}

case class Prepare(c: IController) extends GameState {
  override def exploreCommand(com: Command): Try[_] = {
    com match {
      case _: AddPlayer => c.undoManager.doIt(com)
      case _: NewGrid => c.undoManager.doIt(com)
      case _: StartGame => c.undoManager.doIt(com)
      case _: SetColor => c.undoManager.doIt(com)
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}

case class Play(c: IController) extends GameState {
  override def exploreCommand(com: Command): Try[_] = {
    com match {
      case _: NextPlayer => c.undoManager.doIt(com)
      case _: SetStone => c.undoManager.doIt(com)
      case _ => Failure(new Exception("ILLEGAL COMMAND Play"))
    }
  }
}

case class Finish(c: IController) extends GameState {
  override def exploreCommand(com: Command): Try[_] = {
    com match {
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}

