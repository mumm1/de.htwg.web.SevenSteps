package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.aview.gui.SwingGui
import de.htwg.se.SevenSteps.aview.tui._

import scala.io.StdIn.readLine

object SevenSteps {
  def main(args: Array[String]): Unit = {
    val con = FactoryBasic.newController
    val tui = new Tui(con)
    val gui = new SwingGui(con)
    while (tui.processInputLine(readLine())) {}
  }
}
