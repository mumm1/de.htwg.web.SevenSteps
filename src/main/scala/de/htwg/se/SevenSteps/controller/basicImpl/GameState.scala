package de.htwg.se.SevenSteps.controller.basicImpl

import de.htwg.se.SevenSteps.controller.{IController, IFinish, IPlay, IPrepare}
import de.htwg.se.SevenSteps.util.Command

import scala.util.{Failure, Try}

case class Prepare() extends IPrepare {
  override def exploreCommand(com: Command, c: IController): Try[_] = {
    com match {
      case _: AddPlayer => c.undoManager.doIt(com)
      case _: NewGrid => c.undoManager.doIt(com)
      case _: StartGame => c.undoManager.doIt(com)
      case _: SetColor => c.undoManager.doIt(com)
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}

case class Play() extends IPlay {
  override def exploreCommand(com: Command, c: IController): Try[_] = {
    com match {
      case _: NextPlayer => c.undoManager.doIt(com)
      case _: SetStone => c.undoManager.doIt(com)
      case _: NewGame => c.undoManager.doIt(com)
      case _ => Failure(new Exception("ILLEGAL COMMAND Play"))
    }
  }
}

case class Finish() extends IFinish {
  override def exploreCommand(com: Command, c: IController): Try[_] = {
    com match {
      case _: NewGame => c.undoManager.doIt(com)
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}

