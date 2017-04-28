package de.htwg.se.SevenSteps.model

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec {
  "A Player" should {
    val p = Player("Julius", 50)
    "have a name" in {
      Player("Julius", 50).name should be("Julius")
    }
    "have points" in {
      Player("Julius", 50).points should be(50)
    }
    "can set colors" in {
      p.setColors(List('g', 'b', 'a', 'c')).map.get.contains('g') should be(true)
    }
    "toString look like" in {
      p.toString should be("Julius: Points=50")
    }
  }
  "Players without list" should {
    val pls = Players()
    "Have a curPlayer number" in {
      pls.curPlayer should be(0)
    }
    "Have players" in {
      pls.players.isEmpty should be(true)
      pls.length should be(0)
      pls.nonEmpty should be(false)
    }
  }
  "Players with a List" should {
    var pls = Players()
    val p1 = Player("Julius", 50)
    val p2 = Player("Tobias", 50)
    pls = pls.push(p1)
    pls = pls.push(p2)
    "current player" in {
      pls.curPlayer should be(0)
      pls = pls.next()
      pls.curPlayer should be(1)
      pls.next().curPlayer should be(0)
    }
    "have a players list" in {
      pls.players.isEmpty should be(false)
      pls.length should be(2)
      pls.getCurPlayer should be(p2)
      pls = pls.pop()
      pls.length should be(1)
      pls.next().toString() should be("-> Julius: Points=50\n")
    }
    "set colors to the the players" in {
      val newPls = Players().push(Player("hans")).push(Player("hugo"))
        .setColors("ab".toCharArray.toList)
      newPls(0).map.get.toList should be(List(('a', 0), ('b', 0)))
      newPls(1).map.get.toList should be(List(('a', 0), ('b', 0)))
    }
    "set all Stones of all players" in {
      var newPls = Players().push(Player("hans")).push(Player("hugo"))
        .setColors("ab".toCharArray.toList)
      newPls = newPls.setAllStonesTo(5)
      newPls(0).getStoneNumber should be(10)
      newPls(1).getStoneNumber should be(10)
    }
  }
}