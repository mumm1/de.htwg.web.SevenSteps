package de.htwg.se.SevenSteps

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.{IGrid, IGridFactory}
import de.htwg.se.SevenSteps.model.player.IPlayers
import de.htwg.se.SevenSteps.model.{bag, grid, player}
import net.codingwell.scalaguice.ScalaModule

class SevenStepsModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[IPlayers].to[player.basicImpl.Players]
    bind[IBag].to[bag.basicImpl.Bag]
    bind[IController].to[controller.basicImpl.Controller]
    bind[IGridFactory].to[GridFactory]
    //    install(GridFactory)
  }
}

case class GridFactory() extends IGridFactory {

  import de.htwg.se.SevenSteps.model.grid.basicImpl.Grid

  override def newGrid(colors: String, cols: Int): IGrid = new Grid(colors, cols)
}

class SevenStepsMoc extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[IPlayers].to[player.mockImpl.Players]
    bind[IBag].to[bag.mockImpl.BagMock]
    // bind[IController].to[controller.mockImpl.ControllerMock]

    install(new FactoryModuleBuilder()
      .implement(classOf[IGrid],classOf[grid.mockImpl.GridMock])
      .build(classOf[IGridFactory]))
  }
}
