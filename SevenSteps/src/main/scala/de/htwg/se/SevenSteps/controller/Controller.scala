package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._
import scala.collection.mutable.Stack
import scala.util._

case class Controller(var grid:Grid=new Grid(0,0),var players:List[Player]=Nil) {
  
  var curPlayer:Int=0;
  var gameState:GameState = Prepare(this)
  var undoStack:Stack[Command] = Stack()
  var redoStack:Stack[Command] = Stack()
  var message="Welcome to SevenSteps"
  
  def getCurPlayer():Player={players(curPlayer)}
  def exploreCommand(com: Command):Try[String]={ val explored=gameState.ecploreCommand(com)
    explored match {
    case Success(s) => undoStack.push(com);redoStack.clear();message=s; 
    case Failure(e) => message=e.getMessage}
    explored;}
  
  def undo(){
    if (undoStack.length>0){
      val temp = undoStack.pop() 
      message = "Undo: "+temp.undo(this)
      redoStack.push(temp)
    }
    else
      message="Can't undo now!"
    }
  def redo(){
    if (redoStack.length>0){
      val temp=redoStack.pop() 
      temp.doIt(this) match {
        case Success(s) => message="Redo: "+s
        case Failure(e) => message=e.getMessage()
      }
      undoStack.push(temp)}
    else
      message="Can't redo now!"
    }
  
  override def toString = {
    var text = "############  "+message+"  ############\n\n"
    val len = text.length()
    for(player <- players){
      if(player==players(curPlayer))
        text+="-> "+player.toString()+"\n"
      else
        text+="   "+player.toString()+"\n"
    }
    text+grid.toString()+"#"*(len-2)+"\n"
  }

}

// ##################### Controller Commands ####################### 

trait Command {def doIt(c:Controller):Try[String] 
               def undo(c:Controller):String}

case class AddPlayer(name:String) extends Command{
  val player= Player(name)
  override def doIt(c:Controller):Try[String]={c.players=c.players:+player;Success("Added Player "+name)}
  override def undo(c:Controller):String={val temp=c.players.last;c.players=c.players.take(c.players.length - 1);"Deleted Player "+temp.name}
}

case class NewGrid(colors:String,cols:Int) extends Command{
  var oldGrid:Grid = null
  override def doIt(c:Controller):Try[String]={oldGrid=c.grid;c.grid=new Grid(colors,cols);Success("Build new Grid")}
  override def undo(c:Controller):String={c.grid=oldGrid;"Deleted new Grid"}
}

case class StartGame() extends Command{
  override def doIt(c:Controller):Try[String]={
    if (c.players.length>0){
      c.gameState=new Play(c);c.undoStack.clear();Success("Started the game")
    }else{Failure(new Exception("Can't start the game: Not enough Players"))}}
  override def undo(c:Controller):String={c.undoStack.clear();"You can't undo the start of the game!"}
}

case class NextPlayer() extends Command{
  override def doIt(c:Controller):Try[String]={c.curPlayer+=1;c.curPlayer=c.curPlayer%c.players.length;Success("Player "+c.getCurPlayer.name+" it is your turn!")}
  override def undo(c:Controller):String={c.undoStack.clear();"You can't undo to previous player!"}
}


// ##################### Finate State Machine ####################### 

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
      case command:NextPlayer =>  command.doIt(c)      
      case _                  => Failure(new Exception("ILLEGEL COMMAND"))
    }
  }
}

