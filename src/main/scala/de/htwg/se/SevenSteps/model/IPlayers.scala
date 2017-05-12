package de.htwg.se.SevenSteps.model

import de.htwg.se.SevenSteps.model.impl.Player

trait IPlayers {
  def push(player: Player): IPlayers
  def push(name: String): IPlayers
  def pop(): IPlayers
  def length: Int
  def updateCurPlayer(player: Player): IPlayers
  def next(): IPlayers
  def nonEmpty: Boolean
  def getCurPlayer: Player
  def setColors(colors: List[Char]): IPlayers
  def setAllStonesTo(num: Int): IPlayers
  def apply(i: Int): Player
}
