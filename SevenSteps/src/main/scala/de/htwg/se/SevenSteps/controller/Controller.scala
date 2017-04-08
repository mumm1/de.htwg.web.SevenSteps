package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._

case class Controller(var grid:Grid,var players:List[Player]=Nil) {
  
  def addPlayer(player: Player){players::player::Nil}
  
  override def toString = {
    var text = ""
    text
  }

}

