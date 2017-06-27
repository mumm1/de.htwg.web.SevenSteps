import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState
import scala.xml.PrettyPrinter

object ioWorksheet {
  def save2(cState: ControllerState): Unit = saveString(cState)
  def saveString(cState: ControllerState): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("grid.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(allToXML(cState))
    pw.write(xml)
    pw.close
  }
  def allToXML(cState: ControllerState) = {
    cState.bag.toXML
  }
}