import de.htwg.se.SevenSteps.controller.Controller

val a = "hello world"

val c = Controller()
c.addPlayer("hans")
c.addPlayer("peter")
c.newGrid("abbabbaa", 4)
c.startGame()
c.setStone(0, 0)
c.setStone(0, 1)
c.setStone(1, 1)
c
c.nextPlayer()
c