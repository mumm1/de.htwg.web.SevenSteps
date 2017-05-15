package de.htwg.se.SevenSteps.model

import scala.collection.immutable.Map
import scala.util.Try

trait IPlayers {
  def push(player: IPlayer): IPlayers
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
}

trait IPlayer {
  def setColors(colors: List[Char]): IPlayer
  def setStones(num: Int): IPlayer
  def getStoneNumber: Int
  def placeStone(color: Char, height: Int): Try[IPlayer]
  def incPoints(delta: Int): IPlayer
  def incColor(color: Char, delta: Int): IPlayer
  def name: String
  def points: Int
  def map: Option[Map[Char, Int]]

}
