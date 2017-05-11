
package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._
import de.htwg.se.SevenSteps.util.{Command, Observable, UndoManager}

import scala.collection.mutable
import scala.util._

case class Controller(var grid: Grid = Grid(0, 0),
                      var bag: Bag = Bag("Bag"),
                      var curHeight: Int = 0,
                      var players: Players = Players(),
                      var lastCells: mutable.Stack[(Int, Int)] = mutable.Stack(),
                      var message: String = "Welcome to SevenSteps"
                     ) extends Observable {
  var gameState: GameState = Prepare(this)
  var undoManager = new UndoManager

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
  def doIt(command: Command): Try[Controller] = {
    val result = gameState.exploreCommand(command)
    notifyObservers()
    wrapController(result)
  }
  def wrapController(t: Try[_]): Try[Controller] = {
    t match {
      case Success(_) => Success(this)
      case Failure(e) => Failure(e)
    }
  }
  def nextPlayer(): Try[Controller] = doIt(NextPlayer(this))
  def setStone(row: Int, col: Int): Try[Controller] = doIt(SetStone(row, col, this))
  def undo(): Try[Controller] = {
    val result = undoManager.undo()
    result match {
      case Failure(e) => message = e.getMessage
      case _ =>
    }
    notifyObservers()
    wrapController(result)
  }
  def redo(): Try[Controller] = {
    val result = undoManager.redo()
    result match {
      case Failure(e) => message = e.getMessage
      case _ =>
    }
    notifyObservers()
    wrapController(result)
  }
  override def toString: String = {
    val text = "\n############  " + message + "  ############\n\n"
    val len = text.length()
    text + players.toString() + grid.toString() + "#" * (len - 2) + "\n"
  }
}

