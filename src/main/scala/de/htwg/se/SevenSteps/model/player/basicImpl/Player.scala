package de.htwg.se.SevenSteps.model.player.basicImpl

import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.model.player.{IPlayer, IPlayers}

import scala.collection.immutable.Map
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

case class Players @JsonCreator()(curPlayer: Int, players: Vector[Player]) extends IPlayers {
  def this() = this(0,Vector())
  def push(name: String): Players = push(new Player(name))
  def push(player: Player): Players = copy(players = players :+ player)
  def pop(): Players = {
    copy(players = players.init)
  }
  def length: Int = players.length
  def updateCurPlayer(player: IPlayer): Players = {
    copy(players = players.updated(curPlayer, Player(player.name, player.points, map = player.map)))
  }
  def next(): Players = copy(curPlayer = (curPlayer + 1) % players.length)
  def nonEmpty: Boolean = players.nonEmpty
  def getCurPlayer: Player = players(curPlayer)
  def haveNoStones: Boolean = {
    for (player <- players) {
      if (!player.haveNoStones)
        return false
    }
    true
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
  def getAllPossibleColorsFromAllPlayers: List[Char] = {
    var result: ListBuffer[Char] = ListBuffer()
    for (player <- players) {
      for ((color, num) <- player.map.get) {
        if (num > 0.5 && !result.contains(color.charAt(0))) {
          result += color.charAt(0)
        }
      }
    }
    result.toList
  }
  def reset: Players = {
    val newPlayers = {
      for(player <- players) yield {
        new Player(player.name)
      }
    }
    copy(players=newPlayers)
  }
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

case class Player @JsonCreator()(name: String, points: Int, map: Option[Map[String, Double]]) extends IPlayer {
  def this(name: String) = this(name, 0, None)
  def setColors(colors: List[Char]): Player = {
    var newMap: Map[String, Double] = Map()
    for ((c) <- colors) {
      newMap = newMap + (c.toString -> 0)
    }
    copy(map = Some(newMap))
  }
  def setStones(num: Int): Player = {
    map match {
      case None    => this
      case Some(m) =>
        var newMap: Map[String, Double] = Map()
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
        var num: Double = 0
        for ((_, v) <- m) num += v
        num.toInt
    }

  }
  def placeStone(color: Char,height:Int): Try[Player] = {
    map match {
      case None => Failure(new Exception("You can't place here!"))
      case Some(m) =>
        m.get(color.toString) match {
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
      case Some(m) => copy(map = Some(m.updated(color.toString, m(color.toString) + delta)))
    }
  }
  def haveNoStones: Boolean = {
    map match {
      case None => return true
      case Some(map) =>
        for ((k, v) <- map) {
          if (!(map.apply(k) == 0)) {
            return false
          }
        }
    }
    true
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