package de.htwg.se.SevenSteps

import com.google.inject.Guice
import de.htwg.se.SevenSteps.aview.gui.SwingGui
import de.htwg.se.SevenSteps.aview.tui._
import de.htwg.se.SevenSteps.controller.IController

import scala.io.StdIn.readLine

object SevenSteps {
  def main(args: Array[String]): Unit = {
    //    val con = FactoryBasic.state.newController
    val injector = Guice.createInjector(new SevenStepsModule)
    var con = injector.getInstance(classOf[IController])
    val tui = new Tui(con)
    val gui = new SwingGui(con)
    while (tui.processInputLine(readLine())) {}
  }
}
