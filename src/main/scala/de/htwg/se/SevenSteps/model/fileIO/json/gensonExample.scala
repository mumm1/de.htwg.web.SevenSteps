package de.htwg.se.SevenSteps.model.fileIO.json

import com.google.inject.{Guice, Inject}
import com.owlike.genson._
import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.GameState
import de.htwg.se.SevenSteps.controller.basicImpl._
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.basicImpl.Grid
import de.htwg.se.SevenSteps.model.grid.{IGrid, IGridFactory}
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

trait Person

case class Teacher(name: String) extends Person {
  def this() = this("hans")
}

case class ControllerState2 @JsonCreator()(var players: IPlayers,
                                           var bag: IBag,
                                           var grid: IGrid,
                                           var lastCells: Vector[(Int, Int)],
                                           var gameState: GameState,
                                           var message: String,
                                           var curHeight: Int
                                          ) {
  @Inject()
  def this(players: IPlayers, bag: IBag, gridFactory: IGridFactory) = {
    this(players, bag, gridFactory.newGrid(" ", 1), Vector(), Prepare(), "Welcome to SevenSteps", 0)
  }
}

object gensonExample {
  def main(args: Array[String]): Unit = {
    import CustomGenson.customGenson._
    val injector = Guice.createInjector(new SevenStepsModule)
    //val c = injector.getInstance(classOf[ControllerState])
    val c = ControllerState2(
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
    val s = fromJson[ControllerState2](json)
  }
}
