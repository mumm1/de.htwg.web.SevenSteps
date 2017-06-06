package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.{GridFactory, IGrid}
import de.htwg.se.SevenSteps.model.player.IPlayers

trait Factory extends GridFactory{
  def newPlayers(): IPlayers
  def newBag(): IBag
  def newController: IController
}

object FactoryBasic extends Factory {
  import de.htwg.se.SevenSteps.model.bag.basicImpl.Bag
  import de.htwg.se.SevenSteps.model.grid.basicImpl.Grid
  import de.htwg.se.SevenSteps.model.player.basicImpl.Players
  import de.htwg.se.SevenSteps.controller.basicImpl.Controller
  def newGrid(colors: String, cols: Int): IGrid = new Grid(colors, cols)
  def newPlayers(): IPlayers = new Players()
  def newBag(): IBag = Bag(random = false)
  def newController: IController = new Controller()
}

object FactoryMoc extends Factory {
  import de.htwg.se.SevenSteps.model.bag.mockImpl.Bag
  import de.htwg.se.SevenSteps.model.grid.mockImpl.Grid
  import de.htwg.se.SevenSteps.model.player.mockImpl.Players
  import de.htwg.se.SevenSteps.controller.mockImpl.Controller
  def newGrid(colors: String, cols: Int): IGrid = Grid()
  def newPlayers(): IPlayers = Players()
  def newBag(): IBag = Bag()
  def newController: IController = Controller()
}
