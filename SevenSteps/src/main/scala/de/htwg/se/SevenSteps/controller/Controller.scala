package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._

case class Controller(var grid:Grid,var players:List[Player]=Nil) {
  
  var curPlayer:Player=null;
  def addPlayer(player: Player){
    players=players:+player
    if (players.length==1)
      curPlayer=player
  }
  def color(row:Int,col:Int,color:Char){grid=grid.set(row, col, color)}
  
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

