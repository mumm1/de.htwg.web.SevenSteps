package de.htwg.se.SevenSteps.model

import scala.collection.immutable.Map
import scala.util.{Failure, Success, Try}

case class Players(curPlayer: Int = 0, players: Vector[Player] = Vector()) {
  def push(player: Player): Players = {
    copy(players = players :+ player)
  }
  def push(name: String): Players = {
    copy(players = players :+ Player(name))
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
  def setAllStonesTo(num: Int): Players = {
    val newPlayers = for (p <- players) yield {
      p.setStones(num)
    }
    copy(players = newPlayers)
  }
  def apply(i: Int): Player = players(i)
  override def toString: String = {
    var text = ""
    for (player <- players) {
      if (player == players(curPlayer)) {
        text += "-> " + player.toString() + "\n"
      } else {
        text += "   " + player.toString() + "\n"
      }
    }
    text
  }
}

case class Player(name: String, points: Int = 0, map: Option[Map[Char, Int]] = None) {
  def setColors(colors: List[Char]): Player = {
    var newMap: Map[Char, Int] = Map()
    for ((c) <- colors) {
      newMap = newMap + (c -> 0)
    }
    copy(map = Some(newMap))
  }
  def setStones(num: Int): Player = {
    map match {
      case None    => this
      case Some(m) =>
        var newMap: Map[Char, Int] = Map()
        for ((k, _) <- m) {
          newMap = newMap + (k -> num)
        }
        copy(map = Some(newMap))
    }

  }
  def getStoneNumber: Int = {
    map match {
      case None => 0
      case Some(m) =>
        var num = 0
        for ((_, v) <- m) num += v
        num
    }

  }
  def placeStone(color: Char,height:Int): Try[Player] = {
    map match {
      case None => Failure(new Exception("You can't place here!"))
      case Some(m) =>
        m.get(color) match {
          case None => Failure(new Exception("You can't place here!"))
          case Some(0) => Failure(new Exception("You don't have Stones from color '" + color + "'"))
          case Some(_) => Success(incPoints(height + 1).incColor(color, -1))
    }
  }

  }
  def incPoints(delta: Int): Player = {
    copy(points = points + delta)
  }
  def incColor(color: Char, delta: Int): Player = {
    map match {
      case None => this
      case Some(m) =>
        if(color != '$'){ copy(map = Some(m.updated(color, m(color) + delta)))}
        else this

    }
  }
  override def toString: String = {
    val sb = new StringBuilder
    sb.append(name + ": ")
    map match {
      case None =>
      case Some(m) =>
        for ((k, v) <- m) {
          sb.append(k + "=" + v + ", ")
        }
    }
    sb.append("Points=" + points)
    sb.toString()
  }
}