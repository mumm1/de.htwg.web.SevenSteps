
package de.htwg.se.SevenSteps.controller.mockImpl

import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.controller.basicImpl.Prepare
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.bag.mockImpl.Bag
import de.htwg.se.SevenSteps.model.grid.IGrid
import de.htwg.se.SevenSteps.model.grid.mockImpl.Grid
import de.htwg.se.SevenSteps.model.player.mockImpl.Players
import de.htwg.se.SevenSteps.model.player.IPlayers
import de.htwg.se.SevenSteps.util.UndoManager

import scala.util._

case class Controller() extends IController{
  def addPlayer(name: String): Try[IController] = Success(this)
  def newGrid(colors: String, cols: Int): Try[IController]= Success(this)
  def startGame(): Try[IController]= Success(this)
  def nextPlayer(): Try[IController]= Success(this)
  def setStone(row: Int, col: Int): Try[IController]= Success(this)
  def undo(): Try[IController]= Success(this)
  def redo(): Try[IController]= Success(this)
  def setColor(row: Int, col: Int,color:Char): Try[IController]= Success(this)
  def gameState: GameState = Prepare(this)
  def players: IPlayers = Players()
  def grid: IGrid = Grid()
  def bag: IBag = Bag()
  var message: String = "Hello World, I'am a Mock"
  def undoManager : UndoManager= new UndoManager
}

