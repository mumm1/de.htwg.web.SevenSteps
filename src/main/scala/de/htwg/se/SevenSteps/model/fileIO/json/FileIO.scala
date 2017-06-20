package de.htwg.se.SevenSteps.model.fileIO.json

import com.owlike.genson.{GensonBuilder, ScalaBundle, ScalaGenson}
import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState
import de.htwg.se.SevenSteps.model.fileIO.IFileIO
import scala.io.Source

object CustomGenson {
  val customGenson = new ScalaGenson(
    new GensonBuilder()
      .useIndentation(true) // use pretty-print
      .useClassMetadata(true) // save full class name (traits)
      .useRuntimeType(true)
      .useStrictDoubleParse(true)
      .withBundle(ScalaBundle().useOnlyConstructorFields(true))
      .create()
  )
}

case class FileIO() extends IFileIO {

  import CustomGenson.customGenson._

  def save(cState: ControllerState): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("controllerState.json"))
    pw.write(toJson(cState))
    pw.close
  }
  def load: ControllerState = {
    val source: String = Source.fromFile("controllerState.json").getLines.mkString
    fromJson[ControllerState](source)
  }
}
