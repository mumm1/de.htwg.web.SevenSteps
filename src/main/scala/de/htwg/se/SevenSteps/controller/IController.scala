package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.IGrid
import de.htwg.se.SevenSteps.model.player.IPlayers
import de.htwg.se.SevenSteps.util.Observable
import scala.collection.mutable
import scala.util.Try

trait IController extends Observable{
  var gameState: GameState
  var players: IPlayers
  var grid: IGrid
  var bag: IBag
  var message: String
  var curHeight: Int
  var lastCells: mutable.Stack[(Int, Int)]
  def addPlayer(name: String): Try[IController]
  def newGrid(colors: String, cols: Int): Try[IController]
  def startGame(): Try[IController]
  def nextPlayer(): Try[IController]
  def setStone(row: Int, col: Int): Try[IController]
  def undo(): Try[IController]
  def redo(): Try[IController]
  def setColor(row: Int, col: Int,color:Char): Try[IController]
  def newGame(): Try[IController]
}
