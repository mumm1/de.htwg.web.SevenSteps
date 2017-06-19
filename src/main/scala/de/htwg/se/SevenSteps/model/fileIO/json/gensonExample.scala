package de.htwg.se.SevenSteps.model.fileIO.json

import com.google.inject.Guice
import com.owlike.genson._
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.basicImpl._

object CustomGenson {
  val customGenson = new ScalaGenson(
    new GensonBuilder()
      //.useIndentation(true) // use pretti-print
      .useClassMetadata(true) // save full class name (traits)
      .useRuntimeType(true) //
      .withBundle(ScalaBundle().useOnlyConstructorFields(true)) // also internal vals & vars
      .create()
  )
}

trait Person

case class Teacher(name: String) extends Person {
  def this() = this("hans")
}


object gensonExample {
  def main(args: Array[String]): Unit = {
    import CustomGenson.customGenson._
    val injector = Guice.createInjector(new SevenStepsModule)
    val c = injector.getInstance(classOf[ControllerState])
    val json = toJson(c)
    val s = fromJson[ControllerState](json)
  }
}
