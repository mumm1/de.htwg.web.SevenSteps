
package de.htwg.se.SevenSteps.controller.controllerBasicImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.SevenSteps.{Factory, FactoryBasic, GridFactory, SevenStepsModule}
import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.model._
import de.htwg.se.SevenSteps.model.bagComponent.IBag
import de.htwg.se.SevenSteps.model.gridComponent.IGrid
import de.htwg.se.SevenSteps.model.playerComponent.{IPlayer, IPlayers}
import de.htwg.se.SevenSteps.util.{Command, UndoManager}

import scala.collection.mutable
import scala.util._

case class Controller @Inject() (injector: Injector = Guice.createInjector(new SevenStepsModule)) extends IController{
  var gameState: GameState = Prepare(this)
  var message: String = "Welcome to SevenSteps"
  var curHeight: Int = 0
  var lastCells: mutable.Stack[(Int, Int)] = mutable.Stack()
  var undoManager : UndoManager = new UndoManager
  var grid: IGrid = injector.getInstance(classOf[GridFactory]).create(" ",1)
  var bag: IBag= injector.getInstance(classOf[IBag])
  var players: IPlayers = injector.getInstance(classOf[IPlayers])

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
  def isGameEnd: Boolean = bag.isEmpty && players.haveNoStones
  def finish(): Try[Controller] = {
    gameState = Finish(this)
    message = "Winner is " + getWinningPlayer().name
    Success(this)
  }
  def getWinningPlayer(): IPlayer = {
      var winner = players(0)
    for (i <- 1 to players.length - 1) {
      if (players(i).points > winner.points) {
        winner = players(i)
      }
    }
    winner
  }
  def addPlayer(name: String): Try[Controller] = doIt(AddPlayer(name, this))
  def newGrid(colors: String, cols: Int): Try[Controller] = doIt(NewGrid(colors, cols, this))
  def startGame(): Try[Controller] = doIt(StartGame(this))
  def nextPlayer(): Try[Controller] = doIt(NextPlayer(this))
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
  def isDeadlock: Boolean = {
    val possibleColorsGrid = grid.getColorsWithHeight0
    val possibleColorsPlayer = players.getAllPossibleColorsFromAllPlayers
    for (color <- possibleColorsGrid) {
      if (possibleColorsPlayer.contains(color)) {
        return false
      }
    }
    true
  }
  def setColor(row: Int, col: Int,color:Char): Try[Controller] = doIt(SetColor(row,col,color,this))
  override def toString: String = {
    val text = "\n############  " + message + "  ############\n\n"
    val len = text.length()
    text + players.toString() + grid.toString() + "#" * (len - 2) + "\n"
  }
}

