package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._
import scala.util._

trait Command {def doIt(c:Controller):Try[String] 
               def undoIt(c:Controller)}

case class AddPlayer(name:String) extends Command{
  val player= Player(name)
  override def doIt(c:Controller):Try[String]={c.players=c.players:+player;Success("Added Player: "+player)}
  override def undoIt(c:Controller){c.players=c.players.take(c.players.length - 1)}
}

case class NewGrid(colors:String,cols:Int) extends Command{
  var oldGrid:Grid = null
  override def doIt(c:Controller):Try[String]={oldGrid=c.grid;c.grid=new Grid(colors,cols);Success("Build new Grid")}
  override def undoIt(c:Controller){c.grid=oldGrid}
}

case class StartGame() extends Command{
  override def doIt(c:Controller):Try[String]={
    if (c.players.length>0){
      c.curPlayer=c.players(0);c.gameState=new Play(c);c.undoStack.clear();Success("Started the game")
    }else{Failure(new Exception("Can't start the game: Not enough Players"))}}
  override def undoIt(c:Controller){c.curPlayer=null;c.gameState=new Prepare(c)}
}

trait GameState {def ecploreCommand(com: Command):Try[String]}

case class Prepare(c:Controller) extends GameState{
  override def ecploreCommand(com: Command):Try[String]={
    com match {
      case command:AddPlayer =>  command.doIt(c)
      case command:NewGrid   =>  command.doIt(c)
      case command:StartGame =>  command.doIt(c)
      case _                 =>  Failure(new Exception("ILLEGEL COMMAND"))
    }
  }
}

case class Play(c:Controller) extends GameState{
  override def ecploreCommand(com: Command):Try[String]={
    com match {
      case _                => Failure(new Exception("ILLEGEL COMMAND"))
    }
  }
}




