package de.htwg.se.SevenSteps

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder

import de.htwg.se.SevenSteps.controller
import de.htwg.se.SevenSteps.controller.IController

import de.htwg.se.SevenSteps.model.bagComponent
import de.htwg.se.SevenSteps.model.bagComponent.IBag
import de.htwg.se.SevenSteps.model.gridComponent
import de.htwg.se.SevenSteps.model.gridComponent.IGrid
import de.htwg.se.SevenSteps.model.playerComponent
import de.htwg.se.SevenSteps.model.playerComponent.IPlayers




trait GridFactory {
  def create(colors: String, cols: Int): IGrid
}

class SevenStepsModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[IPlayers]).to(classOf[playerComponent.playerBasicImpl.Players])
    bind(classOf[IBag]).to(classOf[bagComponent.bagBasicImpl.Bag])
    bind(classOf[IController]).to(classOf[controller.controllerBasicImpl.Controller])

    install(new FactoryModuleBuilder()
      .implement(classOf[IGrid],classOf[gridComponent.gridBasicImpl.Grid])
      .build(classOf[GridFactory]))
  }
}
