package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._

trait Command {def doIt(c:Controller) 
               def undoIt(c:Controller)}

case class AddPlayer(name:String) extends Command{
  val player= Player(name)
  override def doIt(c:Controller){c.players=c.players:+player}
  override def undoIt(c:Controller){c.players=c.players.take(c.players.length - 1)}
}

case class NewGrid(colors:String,cols:Int) extends Command{
  var oldGrid:Grid = null
  override def doIt(c:Controller){oldGrid=c.grid;c.grid=new Grid(colors,cols)}
  override def undoIt(c:Controller){c.grid=oldGrid}
}

case class StartGame() extends Command{
  override def doIt(c:Controller){c.curPlayer=c.players(0);c.gameState=new Play(c)}
  override def undoIt(c:Controller){c.curPlayer=null;c.gameState=new Prepare(c)}
}

trait GameState {def ecploreCommand(com: Command)}

case class Prepare(c:Controller) extends GameState{
  override def ecploreCommand(com: Command){
    com match {
      case command:AddPlayer =>  command.doIt(c)
      case command:NewGrid   =>  command.doIt(c)
      case command:StartGame =>  command.doIt(c)
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




