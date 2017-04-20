package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._
import scala.collection.mutable.Stack
import scala.collection.mutable.ListBuffer
import scala.util._

case class Controller(var grid:Grid=new Grid(0,0)) {
  
  var curPlayer:Int=0;
  var curHeight:Int=0;
  var players=Players()
  var lastcell:(Int,Int)=null
  var gameState:GameState = Prepare(this)
  var lastStones:Stack[(Int,Int)]=Stack()
  var undoStack:Stack[Command] = Stack()
  var redoStack:Stack[Command] = Stack()
  var message="Welcome to SevenSteps"
  
  def prepareNewPlayer() {
    curHeight=0
    lastcell=null
    lastStones.clear()
  }
  
  def getCurPlayer():Player={players.getCurPlayer()}
  def doIt(com: Command):Try[String]={ val explored=gameState.ecploreCommand(com)
    explored match {
    case Success(s) => undoStack.push(com);redoStack.clear();message=s; 
    case Failure(e) => message=e.getMessage}
    explored;}
  
  def undo():Try[String]={
    if (undoStack.length>0){
      val temp = undoStack.pop() ; val temp2=temp.undo(this)
       temp2 match {
        case Success(s) => message="Undo: "+s;redoStack.push(temp)
        case Failure(e) => message=e.getMessage()}
      temp2
    }
    else{
      message="Can't undo now!"; Failure(new Exception(message))}
    }
  
  def redo():Try[String]={
    if (redoStack.length>0){
      val temp=redoStack.pop() ; val temp2=temp.doIt(this)
      temp2 match {
        case Success(s) => message="Redo: "+s;undoStack.push(temp)
        case Failure(e) => message=e.getMessage()}
      temp2
    }
    else{
      message="Can't redo now!";Failure(new Exception(message))}
    }
  
  override def toString = {
    var text = "############  "+message+"  ############\n\n"
    val len = text.length()
    text+players.toString()+grid.toString()+"#"*(len-2)+"\n"
  }

}

// ##################### Controller Commands ####################### 

trait Command {def doIt(c:Controller):Try[String] 
               def undo(c:Controller):Try[String]}

case class AddPlayer(name:String) extends Command{
  val player= Player(name)
  override def doIt(c:Controller):Try[String]={c.players=c.players.push(player);Success("Added Player "+name)}
  override def undo(c:Controller):Try[String]={c.players=c.players.pop();Success("Deleted Player")}
}

case class NewGrid(colors:String,cols:Int) extends Command{
  var oldGrid:Grid = null
  override def doIt(c:Controller):Try[String]={oldGrid=c.grid;c.grid=new Grid(colors,cols);Success("Build new Grid")}
  override def undo(c:Controller):Try[String]={c.grid=oldGrid;Success("Deleted new Grid")}
}

case class StartGame() extends Command{
  override def doIt(c:Controller):Try[String]={
    if (c.players.hasMinPlayers){
      c.gameState=new Play(c)
      c.prepareNewPlayer()
      val colors=c.grid.getColors
      c.players=c.players.setColors(colors)
      Success("Started the game")
    }else{Failure(new Exception("Can't start the game: Not enough Players"))}}
  override def undo(c:Controller):Try[String]={c.undoStack.clear();Failure(new Exception("You can't undo the start of the game!"))}
}

case class NextPlayer() extends Command{
  override def doIt(c:Controller):Try[String]={c.players=c.players.next;c.prepareNewPlayer;Success("Player "+c.getCurPlayer.name+" it is your turn!")}
  override def undo(c:Controller):Try[String]={c.undoStack.clear();Failure(new Exception("You can't undo to previous player!"))}
}

case class SetStonde(row:Int,col:Int) extends Command{
  var lastCell:(Int,Int)=null
  def isNeighbour(row:Int,col:Int,c:Controller):Boolean={
    (math.abs(row-c.lastcell._1)+math.abs(col-c.lastcell._2))==1
  }
  override def doIt(c:Controller):Try[String]={
    try{
      val cell  =c.grid.cell(row,col)
      if (c.lastcell!=null)
        if  (!isNeighbour(row,col,c))
          return Failure(new Exception("You have to set a Stone neughboring to the last Stone!"))
        if (c.lastStones.contains((row,col)))    
          return Failure(new Exception("You can't set on a cell twice!"))  
          
      c.getCurPlayer().map.get(cell.color) match {
        case None         => Failure(new Exception("You can't place here!"))
        case Some(0)      => Failure(new Exception("You don't have Stones from color '"+cell.color+"'"))
        case Some(stones) => {  val dif   =c.curHeight-cell.height
                                if (dif==0 | dif==1){                                                            
                                  c.grid=c.grid.set(row, col, cell.height+1)
                                  c.players=c.players.updateCurPlayer(c.players.getCurPlayer().incPoints(cell.height+1).incColor(cell.color,-1))
                                  
                                  
                                  c.curHeight=cell.height+1
                                  lastCell=c.lastcell
                                  c.lastcell=(row,col)
                                  c.lastStones.push((row,col))
                                  Success("You set a stone")
                                }else{Failure(new Exception("You have to set a Stone on height "+(c.curHeight-1)+" or " +c.curHeight))}
        }
      }}catch{ case e:IndexOutOfBoundsException => Failure(new Exception("You can't set a Stone here"))}
    }
  override def undo(c:Controller):Try[String]={    
    val cell=c.grid.cell(row,col)
    c.grid=c.grid.set(row, col, cell.height-1)
    c.players=c.players.updateCurPlayer(c.players.getCurPlayer().incPoints(-cell.height).incColor(cell.color,+1))   
    c.curHeight=cell.height-1
    c.lastcell=lastCell
    c.lastStones.pop()
    ;Success("Take the Stone")}
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
      case command:SetStonde  =>  command.doIt(c)
      case _                  =>  Failure(new Exception("ILLEGEL COMMAND"))
    }
  }
}

