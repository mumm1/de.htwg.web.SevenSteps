package de.htwg.se.SevenSteps.model.player

import de.htwg.se.SevenSteps.model.player.basicImpl.Player

import scala.collection.immutable.Map
import scala.util.Try

trait IPlayers {
  def players: Vector[Player]
  def reset: IPlayers
  def push(name: String): IPlayers
  def pop(): IPlayers
  def length: Int
  def updateCurPlayer(player: IPlayer): IPlayers
  def next(): IPlayers
  def nonEmpty: Boolean
  def getCurPlayer: IPlayer
  def setColors(colors: List[Char]): IPlayers
  def setAllStonesTo(num: Int): IPlayers
  def apply(i: Int): IPlayer
  def haveNoStones: Boolean
  def getAllPossibleColorsFromAllPlayers: List[Char]
}

trait IPlayer {
  def getStoneNumber: Int
  def placeStone(color: Char, height: Int): Try[IPlayer]
  def incPoints(delta: Int): IPlayer
  def incColor(color: Char, delta: Int): IPlayer
  def name: String
  def points: Int
  def map: Option[Map[String, Double]]
  def haveNoStones: Boolean
}
