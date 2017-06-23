
package de.htwg.se.SevenSteps.controller.basicImpl

import com.google.inject.Inject
import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.fileIO.IFileIO
import de.htwg.se.SevenSteps.model.grid.{IGrid, IGridFactory}
import de.htwg.se.SevenSteps.model.player.{IPlayer, IPlayers}
import de.htwg.se.SevenSteps.util.{Command, UndoManager}

import scala.util._

case class ControllerState @JsonCreator()(var players: IPlayers,
                                          var bag: IBag,
                                          var grid: IGrid,
                                          var lastCells: Vector[(Int, Int)],
                                          var gameState: GameState,
                                          var message: String,
                                          var curHeight: Int
                                         ) {
  @Inject()
  def this(players: IPlayers, bag: IBag, gridFactory: IGridFactory) = {
    this(players, bag, gridFactory.newGrid(" ", 1), Vector(), Prepare(), "Welcome to SevenSteps", 0)
  }
}

case class Controller @JsonCreator()
(var state: ControllerState,
 gridFactory: IGridFactory,
 var undoManager: UndoManager,
 fileIO: IFileIO
) extends IController {
  @Inject()
  def this(state: ControllerState, gridFactory: IGridFactory, fileIO: IFileIO) = {
    this(state, gridFactory, new UndoManager, fileIO)
  }
  def prepareNewPlayer(): Unit = {
    for (_ <- state.players.getCurPlayer.getStoneNumber to 6) {
      state.bag.get() match {
        case Some(col: Char) => state.players = state.players.updateCurPlayer(state.players.getCurPlayer.incColor(col, 1))
        case None =>
      }
    }
    state.curHeight = 0
    state.lastCells = Vector()
  }
  def getCurPlayer: IPlayer = {
    state.players.getCurPlayer
  }
  def isGameEnd: Boolean = state.bag.isEmpty && state.players.haveNoStones
  def finish(): Try[Controller] = {
    state.gameState = Finish()
    state.message = "Winner is " + getWinningPlayer.name
    Success(this)
  }
  def getWinningPlayer: IPlayer = {
    var winner = state.players(0)
    for (i <- 1 until state.players.length) {
      if (state.players(i).points > winner.points) {
        winner = state.players(i)
      }
    }
    winner
  }
  def addPlayer(name: String): Try[Controller] = doIt(AddPlayer(name, this))
  def newGrid(colors: String, cols: Int): Try[Controller] = doIt(NewGrid(colors, cols, this))
  def startGame(): Try[Controller] = doIt(StartGame(this))
  def nextPlayer(): Try[Controller] = doIt(NextPlayer(this))
  def doIt(command: Command): Try[Controller] = {
    val result = state.gameState.exploreCommand(command, this)
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
      case Failure(e) => state.message = e.getMessage
      case _ =>
    }
  }
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
  def isDeadlock: Boolean = {
    val possibleColorsGrid = state.grid.getColorsWithHeight0
    val possibleColorsPlayer = state.players.getAllPossibleColorsFromAllPlayers
    for (color <- possibleColorsGrid) {
      if (possibleColorsPlayer.contains(color)) {
        return false
      }
    }
    true
  }
  def setColor(row: Int, col: Int,color:Char): Try[Controller] = doIt(SetColor(row,col,color,this))
  def save(): Unit = {
    fileIO.save(state)
    state.message = "Saved"
    notifyObservers()
  }
  def load(): Unit = {
    state = fileIO.load
    undoManager = UndoManager()
    state.message = "Load: " + state.message
    notifyObservers()
  }
  override def toString: String = {
    val text = "\n############  " + state.message + "  ############\n\n"
    val len = text.length()
    text + state.players.toString + state.grid.toString + "#" * (len - 2) + "\n"
  }
}

