package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.{IGrid, IGridFactory}
import de.htwg.se.SevenSteps.model.player.IPlayers

trait Factory extends IGridFactory {
  def newPlayers(): IPlayers
  def newBag(): IBag
  def newController: IController
}

object FactoryBasic extends Factory {

  import de.htwg.se.SevenSteps.controller.basicImpl.Controller
  import de.htwg.se.SevenSteps.model.bag.basicImpl.Bag
  import de.htwg.se.SevenSteps.model.grid.basicImpl.Grid
  import de.htwg.se.SevenSteps.model.player.basicImpl.Players

  def newController: IController = Controller(newPlayers(), newBag(), this, newGrid(" ", 1))
  def newGrid(colors: String, cols: Int): IGrid = new Grid(colors, cols)
  def newPlayers(): IPlayers = new Players()
  def newBag(): IBag = Bag(random = false)
}

object FactoryMoc extends Factory {

  import de.htwg.se.SevenSteps.controller.mockImpl.ControllerMock
  import de.htwg.se.SevenSteps.model.bag.mockImpl.BagMock
  import de.htwg.se.SevenSteps.model.grid.mockImpl.GridMock
  import de.htwg.se.SevenSteps.model.player.mockImpl.Players
  def newGrid(colors: String, cols: Int): IGrid = GridMock()
  def newPlayers(): IPlayers = Players()
  def newBag(): IBag = BagMock()
  def newController: IController = ControllerMock()
}
