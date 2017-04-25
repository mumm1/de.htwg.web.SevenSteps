package de.htwg.se.SevenSteps.model

import scala.collection.immutable.Map

case class Players(curPlayer: Int = 0, players: Vector[Player] = Vector()) {
  def push(player: Player): Players = {
    copy(players = players :+ player)
  }
  def pop(): Players = {
    copy(players = players.init)
  }
  def length: Int = {
    players.length
  }
  def updateCurPlayer(player: Player): Players = {
    copy(players = players.updated(curPlayer, player))
  }
  def next(): Players = {
    copy(curPlayer = (curPlayer + 1) % players.length)
  }
  def nonEmpty: Boolean = {
    players.nonEmpty
  }
  def getCurPlayer: Player = {
    players(curPlayer)
  }
  def setColors(colors: List[Char]): Players = {
    val newPlayers = for (p <- players) yield {
      p.setColors(colors)
    }
    copy(players = newPlayers)
  }
  override def toString: String = {
    var text = ""
    for (player <- players) {
      if (player == players(curPlayer)) {
        text += "-> " + player.toString() + "\n" //+" CurHeight="+curHeight+"\n"
      }
      else {
        text += "   " + player.toString() + "\n"
      }
    }
    text
  }
}

case class Player(name: String, points: Int = 0, map: Map[Char, Int] = Map[Char, Int]()) {
  def setColors(colors: List[Char]): Player = {
    var newMap: Map[Char, Int] = Map()
    for ((c) <- colors) {
      newMap = newMap + (c -> 3)
    }
    copy(map = newMap)
  }
  def incPoints(delta: Int): Player = {
    copy(points = points + delta)
  }
  def incColor(color: Char, delta: Int): Player = {
    copy(map = map.updated(color, map(color) + delta))
  }
  def getStoneNumber: Int = {
    var num = 0
    for ((_, v) <- map) num += v
    num
  }
  override def toString: String = {
    val sb = new StringBuilder
    sb.append(name + ": ")
    for ((k, v) <- map) {
      sb.append(k + "=" + v + ", ")
    }
    sb.append("Points=" + points)
    sb.toString()
  }
}