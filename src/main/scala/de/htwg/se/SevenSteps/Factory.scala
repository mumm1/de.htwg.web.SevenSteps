package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.bagComponent.IBag
import de.htwg.se.SevenSteps.model.gridComponent.{GridFactory, IGrid}
import de.htwg.se.SevenSteps.model.playerComponent.IPlayers

trait Factory extends GridFactory{
  def newPlayers(): IPlayers
  def newBag(): IBag
  def newController: IController
}

object FactoryBasic extends Factory {
  import de.htwg.se.SevenSteps.model.bagComponent.bagBasicImpl.Bag
  import de.htwg.se.SevenSteps.model.gridComponent.gridBasicImpl.Grid
  import de.htwg.se.SevenSteps.model.playerComponent.playerBasicImpl.Players
  import de.htwg.se.SevenSteps.controller.controllerBasicImpl.Controller
  def newGrid(colors: String, cols: Int): IGrid = new Grid(colors, cols)
  def newPlayers(): IPlayers = new Players()
  def newBag(): IBag = Bag(random = false)
  def newController: IController = new Controller()
}

object FactoryMoc extends Factory {
  import de.htwg.se.SevenSteps.model.bagComponent.bagMocImpl.Bag
  import de.htwg.se.SevenSteps.model.gridComponent.gridMocImpl.Grid
  import de.htwg.se.SevenSteps.model.playerComponent.playerMocImpl.Players
  import de.htwg.se.SevenSteps.controller.controllerMockImpl.Controller
  def newGrid(colors: String, cols: Int): IGrid = Grid()
  def newPlayers(): IPlayers = Players()
  def newBag(): IBag = Bag()
  def newController: IController = Controller()
}
