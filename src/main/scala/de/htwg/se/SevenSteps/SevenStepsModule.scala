package de.htwg.se.SevenSteps

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.SevenSteps.model.bagComponent.IBag
import de.htwg.se.SevenSteps.model.gridComponent.IGrid
import de.htwg.se.SevenSteps.model.playerComponent.IPlayers
import de.htwg.se.SevenSteps.model.gridComponent
import de.htwg.se.SevenSteps.model.playerComponent
import de.htwg.se.SevenSteps.model.bagComponent

trait GridFactory {
  def create(colors: String, cols: Int): IGrid
}

class SevenStepsModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[IPlayers]).to(classOf[playerComponent.playerBasicImpl.Players])
    bind(classOf[IGrid]).to(classOf[gridComponent.gridBasicImpl.Grid])
    bind(classOf[IBag]).to(classOf[bagComponent.bagBasicImpl.Bag])

    install(new FactoryModuleBuilder()
      .implement(classOf[IGrid],classOf[gridComponent.gridBasicImpl.Grid])
      .build(classOf[GridFactory]))
  }
}
