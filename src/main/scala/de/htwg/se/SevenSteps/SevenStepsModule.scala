package de.htwg.se.SevenSteps

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.{GridFactory, IGrid}
import de.htwg.se.SevenSteps.model.{bag, grid, player}
import de.htwg.se.SevenSteps.model.player.IPlayers
import net.codingwell.scalaguice.ScalaModule

class SevenStepsModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[IPlayers].to[player.basicImpl.Players]
    bind[IBag].to[bag.basicImpl.Bag]
    bind[IController].to[controller.basicImpl.Controller]

    install(new FactoryModuleBuilder()
      .implement(classOf[IGrid],classOf[grid.basicImpl.Grid])
      .build(classOf[GridFactory]))
  }
}

class SevenStepsMoc extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[IPlayers].to[player.mockImpl.Players]
    bind[IBag].to[bag.mockImpl.BagMock]
    bind[IController].to[controller.mockImpl.ControllerMock]

    install(new FactoryModuleBuilder()
      .implement(classOf[IGrid],classOf[grid.mockImpl.GridMock])
      .build(classOf[GridFactory]))
  }
}
