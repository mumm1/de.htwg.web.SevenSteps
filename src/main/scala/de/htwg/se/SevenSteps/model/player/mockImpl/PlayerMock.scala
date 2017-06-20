package de.htwg.se.SevenSteps.model.player.mockImpl

import de.htwg.se.SevenSteps.model.player.{IPlayer, IPlayers}

import scala.collection.immutable.Map
import scala.util.{Success, Try}

case class Players() extends IPlayers {
  def push(name: String): IPlayers = this
  def pop(): IPlayers = this
  def length: Int = 1
  def updateCurPlayer(player: IPlayer): IPlayers = this
  def next(): IPlayers = this
  def nonEmpty: Boolean = true
  def getCurPlayer: IPlayer = Player()
  def setColors(colors: List[Char]): IPlayers = this
  def setAllStonesTo(num: Int): IPlayers = this
  def apply(i: Int): IPlayer = Player()
  def haveNoStones: Boolean = false
  def getAllPossibleColorsFromAllPlayers: List[Char] = List()
  def reset: IPlayers = this
}

case class Player() extends IPlayer {
  def getStoneNumber: Int = 2
  def placeStone(color: Char, height: Int): Try[IPlayer] = Success(this)
  def incPoints(delta: Int): IPlayer = this
  def incColor(color: Char, delta: Int): IPlayer = this
  def name: String = "Hans"
  def points: Int = 10
  def map: Option[Map[String, Double]] = None
  def haveNoStones: Boolean = true
}