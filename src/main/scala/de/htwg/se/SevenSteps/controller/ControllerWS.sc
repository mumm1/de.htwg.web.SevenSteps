import de.htwg.se.SevenSteps.model.impl.Players
import de.htwg.se.SevenSteps.model.playerComponent.playerBasicImpl.{Player, Players}

val p1 = new Player("Player1")
val p2 = new Player("Player2")
val p3 = new Player("Player3")
var players: Players = Players()
p1.incPoints(5)
p2.incPoints(33)
p3.incPoints(12)


players.push(p1)
players.push(p2)
players.push(p3)
def win(): Unit = {
  val winner = players.players.max
}
