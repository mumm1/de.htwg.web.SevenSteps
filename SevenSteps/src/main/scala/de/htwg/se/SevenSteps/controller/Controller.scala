package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._
import scala.collection.mutable.Stack
import scala.util._

case class Controller(var grid:Grid=new Grid(1,1),var players:List[Player]=Nil) {
  
  var curPlayer:Player=null;
  var gameState:GameState = Prepare(this)
  var undoStack:Stack[Command] = Stack()
  var redoStack:Stack[Command] = Stack()
  
  def exploreCommand(com: Command){
    undoStack.push(com);
    gameState.ecploreCommand(com) match {
    case Success(s) => redoStack.clear();println(s); 
    case Failure(e) => undoStack.pop();println(e.getMessage);}}
  
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
    var text = "\n"
    for(player <- players){
      if(player==curPlayer)
        text+="-> "+player.toString()+"\n"
      else
        text+="   "+player.toString()+"\n"
    }
    text+grid.toString()
  }

}

