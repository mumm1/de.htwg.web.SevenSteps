package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model.bagComponent.IBag
import de.htwg.se.SevenSteps.model.gridComponent.IGrid
import de.htwg.se.SevenSteps.model.playerComponent.IPlayers
import de.htwg.se.SevenSteps.util.{Observable, UndoManager}

import scala.util.Try

trait IController extends Observable{
  def addPlayer(name: String): Try[IController]
  def newGrid(colors: String, cols: Int): Try[IController]
  def startGame(): Try[IController]
  def nextPlayer(): Try[IController]
  def setStone(row: Int, col: Int): Try[IController]
  def undo(): Try[IController]
  def redo(): Try[IController]
  def setColor(row: Int, col: Int,color:Char): Try[IController]
  def gameState: GameState
  def players: IPlayers
  def grid: IGrid
  def bag: IBag
  var message: String
  def undoManager : UndoManager
}
