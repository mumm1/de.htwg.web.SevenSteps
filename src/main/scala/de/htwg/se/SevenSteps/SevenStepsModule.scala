package de.htwg.se.SevenSteps

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.bag
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid
import de.htwg.se.SevenSteps.model.grid.{GridFactory, IGrid}
import de.htwg.se.SevenSteps.model.player
import de.htwg.se.SevenSteps.model.player.IPlayers


class SevenStepsModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[IPlayers]).to(classOf[player.basicImpl.Players])
    bind(classOf[IBag]).to(classOf[bag.basicImpl.Bag])
    bind(classOf[IController]).to(classOf[controller.basicImpl.Controller])

    install(new FactoryModuleBuilder()
      .implement(classOf[IGrid],classOf[grid.basicImpl.Grid])
      .build(classOf[GridFactory]))
  }
}

class SevenStepsMoc extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[IPlayers]).to(classOf[player.mockImpl.Players])
    bind(classOf[IBag]).to(classOf[bag.mockImpl.BagMock])
    bind(classOf[IController]).to(classOf[controller.mockImpl.ControllerMock])

    install(new FactoryModuleBuilder()
      .implement(classOf[IGrid],classOf[grid.mockImpl.GridMock])
      .build(classOf[GridFactory]))
  }
}
