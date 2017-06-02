package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.controller.controllerBasicImpl._
import de.htwg.se.SevenSteps.model.bagComponent.IBag
import de.htwg.se.SevenSteps.model.gridComponent.IGrid
import de.htwg.se.SevenSteps.model.playerComponent.IPlayers
import de.htwg.se.SevenSteps.util.Observable

import scala.util.Try

trait IController extends Observable{
  def addPlayer(name: String): Try[Controller]
  def newGrid(colors: String, cols: Int): Try[Controller]
  def startGame(): Try[Controller]
  def nextPlayer(): Try[Controller]
  def setStone(row: Int, col: Int): Try[Controller]
  def undo(): Try[Controller]
  def redo(): Try[Controller]
  def setColor(row: Int, col: Int,color:Char): Try[Controller]
  def gameState: GameState
  def players: IPlayers
  def grid: IGrid
  def bag: IBag
  var message: String
}
