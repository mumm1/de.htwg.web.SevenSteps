package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.aview.gui.SwingGui
import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.aview.tui._
import de.htwg.se.SevenSteps.controller.controllerBasicImpl.Controller
import de.htwg.se.SevenSteps.model.{ModelFactory1, ModelMocFactory}

import scala.io.StdIn.readLine

object SevenSteps {
  def main(args: Array[String]): Unit = {
    val con = new Controller(modelFactory = ModelFactory1)
    val tui = new Tui(con)
    val gui = new SwingGui(con)
    while (tui.processInputLine(readLine())) {}
  }
}
