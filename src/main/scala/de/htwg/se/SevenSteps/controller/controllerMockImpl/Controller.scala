
package de.htwg.se.SevenSteps.controller.controllerMockImpl

import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.controller.controllerBasicImpl.Prepare
import de.htwg.se.SevenSteps.model.bagComponent.IBag
import de.htwg.se.SevenSteps.model.bagComponent.bagMocImpl.Bag
import de.htwg.se.SevenSteps.model.gridComponent.IGrid
import de.htwg.se.SevenSteps.model.gridComponent.gridMocImpl.Grid
import de.htwg.se.SevenSteps.model.playerComponent.playerMocImpl.Players
import de.htwg.se.SevenSteps.model.playerComponent.IPlayers
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

