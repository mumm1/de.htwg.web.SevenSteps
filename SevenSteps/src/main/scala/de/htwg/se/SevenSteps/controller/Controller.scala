package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._
import scala.collection.mutable.Stack
import scala.util._

case class Controller(var grid:Grid=new Grid(1,1),var players:List[Player]=Nil) {
  
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
      val temp=undoStack.pop() 
      temp.undo(this)
      redoStack.push(temp)}
    }
  def redo(){
    if (redoStack.length>0){
      val temp=redoStack.pop() 
      temp.doIt(this)
      undoStack.push(temp)}
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

