package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevennSteps.aview.tui._
import de.htwg.se.SevenSteps.model._

object Hello {
  def main(args: Array[String]): Unit = {
    println("Welcome to SevenSteps")
    val con = new Controller()
    val tui = new Tui(con)
  
    while (tui.processInputLine(readLine())) {}
  }
}
