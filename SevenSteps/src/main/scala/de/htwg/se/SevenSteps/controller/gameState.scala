package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._

trait Command {def doIt(c:Controller)}
case class AddPlayer(player:Player) extends Command{override def doIt(c:Controller){
  c.players=c.players:+player
    if (c.players.length==1)
      c.curPlayer=player}}

trait GameState {def ecploreCommand(com: Command)}

case class Prepare(c:Controller) extends GameState{
  override def ecploreCommand(com: Command){
    com match {
      case command:AddPlayer =>  command.doIt(c)
      case _                 =>  println("ILLEGEL COMMAND")
    }
  }
}

case class Play(c:Controller) extends GameState{
  override def ecploreCommand(com: Command){
    com match {
      case _                => println("ILLEGEL COMMAND")
    }
  }
}




