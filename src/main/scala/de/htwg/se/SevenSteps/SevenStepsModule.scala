package de.htwg.se.SevenSteps

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.fileIO.IFileIO
import de.htwg.se.SevenSteps.model.grid.{IGrid, IGridFactory}
import de.htwg.se.SevenSteps.model.player.IPlayers
import de.htwg.se.SevenSteps.model.{bag, fileIO, grid, player}
import net.codingwell.scalaguice.ScalaModule

class SevenStepsModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[IPlayers].to[player.basicImpl.Players]
    bind[IBag].to[bag.basicImpl.Bag]
    bind[IController].to[controller.basicImpl.Controller]
    bind[IFileIO].to[fileIO.json.FileIO]
    install(new FactoryModuleBuilder()
      .implement(classOf[IGrid], classOf[grid.basicImpl.Grid])
      .build(classOf[IGridFactory]))
  }
}