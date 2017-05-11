
package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._
import de.htwg.se.SevenSteps.util.{Command, Observable}

import scala.collection.mutable
import scala.util._

case class Controller(var grid: Grid = Grid(0, 0),
                      var bag: Bag = Bag("Bag"),
                      var curHeight: Int = 0,
                      var players: Players = Players(),
                      var lastCells: mutable.Stack[(Int, Int)] = mutable.Stack(),
                      var undoStack: mutable.Stack[Command] = mutable.Stack(),
                      var redoStack: mutable.Stack[Command] = mutable.Stack(),
                      var message: String = "Welcome to SevenSteps"
                     ) extends Observable {
  var gameState: GameState = Prepare(this)


  def prepareNewPlayer(): Unit = {
    for (_ <- getCurPlayer.getStoneNumber to 6) {
      players = players.updateCurPlayer(players.getCurPlayer.incColor(bag.pull(), 1))
    }
    curHeight = 0
    lastCells.clear()
  }
  def getCurPlayer: Player = {
    players.getCurPlayer
  }
  def addPlayer(name: String): Try[Controller] = doIt(AddPlayer(name, this))
  def newGrid(colors: String, cols: Int): Try[Controller] = doIt(NewGrid(colors, cols, this))
  def startGame(): Try[Controller] = doIt(StartGame(this))
  def doIt(com: Command): Try[Controller] = {
    val explored = gameState.exploreCommand(com)
    explored match {
      case Success(_) =>
        undoStack.push(com)
        redoStack.clear()
      case Failure(e) => message = e.getMessage
    }
    notifyObservers()
    wrapController(explored)
  }
  def nextPlayer(): Try[Controller] = doIt(NextPlayer(this))
  def setStone(row: Int, col: Int): Try[Controller] = doIt(SetStone(row, col, this))
  def undo(): Try[Controller] = {
    if (undoStack.nonEmpty) {
      val temp = undoStack.pop()
      val temp2 = temp.undo()
      temp2 match {
        case Success(_) =>
          message = "Undo: " + message
          redoStack.push(temp)
        case Failure(e) => message = e.getMessage
      }
      notifyObservers()
      wrapController(temp2)
    } else {
      message = "Can't undo now!"
      Failure(new Exception(message))
    }
  }
  def redo(): Try[Controller] = {
    if (redoStack.nonEmpty) {
      val temp = redoStack.pop()
      val temp2 = temp.doIt()
      temp2 match {
        case Success(s) =>
          message = "Redo: " + s
          undoStack.push(temp)
        case Failure(e) => message = e.getMessage
      }
      notifyObservers()
      wrapController(temp2)
    } else {
      message = "Can't redo now!"
      Failure(new Exception(message))
    }
  }
  def wrapController(t: Try[_]): Try[Controller] = {
    t match {
      case Success(_) => Success(this)
      case Failure(e) => Failure(e)
    }
  }
  override def toString: String = {
    val text = "\n############  " + message + "  ############\n\n"
    val len = text.length()
    text + players.toString() + grid.toString() + "#" * (len - 2) + "\n"
  }
}

// ##################### Finite State Machine #######################
trait GameState {
  def exploreCommand(com: Command): Try[_]
}

case class Prepare(c: Controller) extends GameState {
  override def exploreCommand(com: Command): Try[_] = {
    com match {
      case command: AddPlayer => command.doIt()
      case command: NewGrid => command.doIt()
      case command: StartGame => command.doIt()
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}

case class Play(c: Controller) extends GameState {
  override def exploreCommand(com: Command): Try[_] = {
    com match {
      case command: NextPlayer => command.doIt()
      case command: SetStone => command.doIt()
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}

