package de.htwg.se.SevenSteps.model.playerComponent.playerMocImpl

import de.htwg.se.SevenSteps.model.playerComponent.{IPlayer, IPlayers}

import scala.collection.immutable.Map
import scala.util.{Failure,Try}

case class PlayersMoc() extends IPlayers {
  def push(name: String): IPlayers = this
  def pop(): IPlayers = this
  def length: Int = 0
  def updateCurPlayer(player: IPlayer): IPlayers = this
  def next(): IPlayers = this
  def nonEmpty: Boolean = false
  def getCurPlayer: IPlayer = PlayerMoc()
  def setColors(colors: List[Char]): IPlayers = this
  def setAllStonesTo(num: Int): IPlayers = this
  def apply(i: Int): IPlayer = PlayerMoc()
  def haveNoStones: Boolean = true
  def getAllPossibleColorsFromAllPlayers: List[Char] = List()
}

case class PlayerMoc() extends IPlayer {
  def getStoneNumber: Int = 0
  def placeStone(color: Char, height: Int): Try[IPlayer] = Failure(new Exception)
  def incPoints(delta: Int): IPlayer = this
  def incColor(color: Char, delta: Int): IPlayer = this
  def name: String = "Hans"
  def points: Int = 10
  def map: Option[Map[Char, Int]] = None
  def haveNoStones(): Boolean = true
}