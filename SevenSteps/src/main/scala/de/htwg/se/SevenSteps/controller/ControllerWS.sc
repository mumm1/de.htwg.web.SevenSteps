package de.htwg.se.SevenSteps.controller
import de.htwg.se.SevenSteps.model._

object ControllerWS {
  var con = new Controller()
  con.exploreCommand(new AddPlayer("Hans"))
  con.exploreCommand(new AddPlayer("PeteRr"))
  con.toString
}