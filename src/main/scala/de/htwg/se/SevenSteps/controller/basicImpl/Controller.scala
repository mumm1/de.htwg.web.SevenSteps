
package de.htwg.se.SevenSteps.controller.basicImpl

import com.google.inject.Inject
import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.{IGrid, IGridFactory}
import de.htwg.se.SevenSteps.model.player.{IPlayer, IPlayers}
import de.htwg.se.SevenSteps.util.{Command, UndoManager}

import scala.collection.mutable
import scala.util._

case class ControllerState @JsonCreator()(var players: IPlayers,
                                          var bag: IBag,
                                          var grid: IGrid,
                                          var gameState: GameState = Prepare(),
                                          var message: String = "Welcome to SevenSteps",
                                          var curHeight: Int = 0,
                                          var lastCells: mutable.Stack[(Int, Int)] = mutable.Stack()) {
  @Inject()
  def this(players: IPlayers, bag: IBag, gridFactory: IGridFactory) = {
    this(players, bag, gridFactory.newGrid(" ", 1))
  }
}

case class Controller @JsonCreator()
(var c: ControllerState,
 var gridFactory: IGridFactory,
 var undoManager: UndoManager
) extends IController {
  @Inject()
  def this(cState: ControllerState, gridFactory: IGridFactory) = {
    this(cState, gridFactory, new UndoManager)
  }
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
  override def bag: IBag = c.bag
  override def bag_=(bag: IBag) {
    this.c.bag = bag
  }
  override def curHeight: Int = c.curHeight
  override def curHeight_=(curHeight: Int) {
    this.c.curHeight = curHeight
  }
  override def lastCells: mutable.Stack[(Int, Int)] = c.lastCells
  override def lastCells_=(lastCells: mutable.Stack[(Int, Int)]) {
    this.c.lastCells = lastCells
  }
  def getCurPlayer: IPlayer = {
    players.getCurPlayer
  }
  override def players: IPlayers = c.players
  override def players_=(players: IPlayers) {
    this.c.players = players
  }
  def isGameEnd: Boolean = bag.isEmpty && players.haveNoStones
  def finish(): Try[Controller] = {
    gameState = Finish()
    message = "Winner is " + getWinningPlayer.name
    Success(this)
  }
  def getWinningPlayer: IPlayer = {
      var winner = players(0)
    for (i <- 1 until players.length) {
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
  def setStone(row: Int, col: Int): Try[Controller] = doIt(SetStone(row, col, this))
  def newGame(): Try[Controller] = doIt(NewGame(this))
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
  def doIt(command: Command): Try[Controller] = {
    val result = gameState.exploreCommand(command, this)
    unpackError(result)
    notifyObservers()
    wrapController(result)
  }
  override def gameState: GameState = c.gameState
  override def gameState_=(gameState: GameState) {
    this.c.gameState = gameState
  }
  override def toString: String = {
    val text = "\n############  " + message + "  ############\n\n"
    val len = text.length()
    text + players.toString + grid.toString + "#" * (len - 2) + "\n"
  }
  override def grid: IGrid = c.grid
  override def grid_=(grid: IGrid) {
    this.c.grid = grid
  }
  override def message: String = c.message
  override def message_=(message: String) {
    this.c.message = message
  }
}

