
package de.htwg.se.SevenSteps.controller

//import de.htwg.se.SevenSteps.model.impl.Bag
import de.htwg.se.SevenSteps.model.{IGrid, IPlayers, IPlayer, IBag, ModelFactory, ModelFactory1}
import de.htwg.se.SevenSteps.util.{Command, Observable, UndoManager}

import scala.collection.mutable
import scala.util._

case class Controller(var grid: IGrid = ModelFactory1.newGrid(),
                      var bag: IBag = ModelFactory1.newBag(),
                      var curHeight: Int = 0,
                      var players: IPlayers = ModelFactory1.newPlayers(),
                      var lastCells: mutable.Stack[(Int, Int)] = mutable.Stack(),
                      var message: String = "Welcome to SevenSteps",
                      modelFactory: ModelFactory = ModelFactory1
                     ) extends Observable {
  var gameState: GameState = Prepare(this)
  var undoManager = new UndoManager

  def prepareNewPlayer(): Unit = {
    for (_ <- players.getCurPlayer.getStoneNumber to 6) {
      bag.get() match {
        case Some(col: Char) => players = players.updateCurPlayer(players.getCurPlayer.incColor(col, 1))
        case None =>
      }
    }
    curHeight = 0
    lastCells.clear()
  }
  def getCurPlayer: IPlayer = {
    players.getCurPlayer
  }
  def win(): Option[IPlayer] = {
    if (bag.isEmpty() && players.haveNoStones) {
      var winner = players.getPlayerList.apply(0)
    for (i <- 1 to players.length - 1) {
      if (players.getPlayerList(i).points > winner.points) {
        winner = players.getPlayerList(i)
      }
    }
      Some(winner)
    } else {
      None
    }
  }
  def addPlayer(name: String): Try[Controller] = doIt(AddPlayer(name, this))
  def newGrid(colors: String, cols: Int): Try[Controller] = doIt(NewGrid(colors, cols, this))
  def doIt(command: Command): Try[Controller] = {
    val result = gameState.exploreCommand(command)
    unpackError(result)
    notifyObservers()
    wrapController(result)
  }
  def wrapController(t: Try[_]): Try[Controller] = {
    t match {
      case Success(_) => Success(this)
      case Failure(e) => Failure(e)
    }
  }
  def unpackError(e: Try[_]): Unit = {
    e match {
      case Failure(e) => message = e.getMessage
      case _ =>
    }
  }
  def startGame(): Try[Controller] = doIt(StartGame(this))
  def nextPlayer(): Try[Controller] = doIt(NextPlayer(this))
  def setStone(row: Int, col: Int): Try[Controller] = doIt(SetStone(row, col, this))
  def undo(): Try[Controller] = {
    val result = undoManager.undo()
    unpackError(result)
    notifyObservers()
    wrapController(result)
  }
  def redo(): Try[Controller] = {
    val result = undoManager.redo()
    unpackError(result)
    notifyObservers()
    wrapController(result)
  }
  override def toString: String = {
    val text = "\n############  " + message + "  ############\n\n"
    val len = text.length()
    text + players.toString() + grid.toString() + "#" * (len - 2) + "\n"
  }
}

