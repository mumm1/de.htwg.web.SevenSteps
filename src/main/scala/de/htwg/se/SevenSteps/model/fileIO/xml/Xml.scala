package de.htwg.se.SevenSteps.model.fileIO.xml

import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState
import de.htwg.se.SevenSteps.model.fileIO.IFileIO
import scala.xml.PrettyPrinter

case class Xml() extends IFileIO {
  var help: ControllerState = null
  def save(cState: ControllerState): Unit = help = cState
  def load: ControllerState = {
    //load2
    help
  }
  def load2: Unit = {
    val file = scala.xml.XML.loadFile("controllerState.xml")
    val sizeAttr = (file \\ "grid" \ "@mes")
    print("HUHUHUHU" + sizeAttr)
  }
  def save5(cState: ControllerState): Unit = saveString(cState)
  def saveString(cState: ControllerState): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("controllerState.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(save3(cState))
    pw.write(xml)
    pw.close
  }
  def save3(c: ControllerState) = {
    <game players={c.players.toXML()} bag={c.bag.toXML} grid={c.grid.toXML()} rest={rest(c)}></game>
  }
  def rest(state: ControllerState) = {
    <rest mes={state.message} cur={state.curHeight.toString} last={lastCellsToXML(state.lastCells)}>
    </rest>
  }
  def lastCellsToXML(stones: Vector[(Int, Int)]) = {
    stones.map { entry =>
      val (row, col) = entry
      <cell row={row.toString} col={col.toString}>
      </cell>
    }
  }
  def allToXML(cState: ControllerState) = {
    cState.bag.toXML
  }
}
