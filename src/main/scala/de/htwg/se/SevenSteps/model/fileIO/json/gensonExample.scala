package de.htwg.se.SevenSteps.model.fileIO.json

import com.google.inject.Guice
import com.owlike.genson._
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.basicImpl._
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.basicImpl.Grid
import de.htwg.se.SevenSteps.model.player.IPlayers

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

object gensonExample {
  def main(args: Array[String]): Unit = {
    import CustomGenson.customGenson._
    val injector = Guice.createInjector(new SevenStepsModule)
    //val c = injector.getInstance(classOf[ControllerState])
    val c = ControllerState(
      injector.getInstance(classOf[IPlayers]),
      injector.getInstance(classOf[IBag]),
      new Grid("aa", 2),
      Vector(),
      Prepare(),
      "",
      0
    )
    val json = toJson(c)
    println(json)
    val s = fromJson[ControllerState](json)
  }
}
