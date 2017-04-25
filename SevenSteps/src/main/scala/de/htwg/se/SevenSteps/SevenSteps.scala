package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevennSteps.aview.tui._

object Hello {
  def main(args: Array[String]): Unit = {
    val con = new Controller()
    val tui = new Tui(con)
    while (tui.processInputLine(readLine())) {}
  }
}
