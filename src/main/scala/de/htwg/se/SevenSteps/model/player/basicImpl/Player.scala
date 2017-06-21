package de.htwg.se.SevenSteps.model.player.basicImpl

import de.htwg.se.SevenSteps.model.player.{IPlayer, IPlayers}

import scala.collection.immutable.Map
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

case class Players (curPlayer: Int, players: Vector[Player]) extends IPlayers {
  def this() = this(0,Vector())
  def push(name: String): Players = push(Player(name))
  def push(player: Player): Players = copy(players = players :+ player)
  def pop(): Players = {
    copy(players = players.init)
  }
  def length: Int = players.length
  JN"WA?/WESAY<"
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
      case Some(m) => copy(map = Some(m.updated(color, m(color) + delta)))
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

++
9056 wsayj
cv +
ß