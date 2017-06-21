package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.IGrid
import de.htwg.se.SevenSteps.model.player.IPlayers
import de.htwg.se.SevenSteps.util.{Observable, UndoManager}
import scala.collection.mutable
import scala.util.Try

trait IController extends Observable{
  var message: String
  var lastCells: mutable.Stack[(Int, Int)]
  var curHeight: Int
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
  def undoManager : UndoManager
  def newGame(): Try[IController]
}
