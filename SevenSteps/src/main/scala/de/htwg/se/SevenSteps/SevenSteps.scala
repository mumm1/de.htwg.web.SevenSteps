package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.model._

object Hello {
  def main(args: Array[String]): Unit = {
    println("Hello, World")
    var con = new Controller()
    con.exploreCommand(new AddPlayer("Hans"))
    con.exploreCommand(new AddPlayer("PeteRr"))
    println(con.toString)
    con.exploreCommand(new NewGrid("ab sdd",3))
    println(con.toString)
    con.exploreCommand(new StartGame())
    con.exploreCommand(new AddPlayer("Hugo"))
    

  }
}
