package de.htwg.se.SevenSteps.model

import de.htwg.se.SevenSteps.model.bagComponent.IBag
import de.htwg.se.SevenSteps.model.bagComponent.bagBasicImpl.Bag
import de.htwg.se.SevenSteps.model.gridComponent.IGrid
import de.htwg.se.SevenSteps.model.gridComponent.gridBasicImpl.Grid
import de.htwg.se.SevenSteps.model.playerComponent.IPlayers
import de.htwg.se.SevenSteps.model.playerComponent.playerBasicImpl.Players

trait ModelFactory {
  def newGrid(colors: String, cols: Int): IGrid
  def newGrid(): IGrid
  def newPlayers(): IPlayers
  def newBag(): IBag
}

object ModelFactory1 extends ModelFactory {
  def newGrid(colors: String, cols: Int): IGrid = new Grid(colors, cols)
  def newGrid(): IGrid = new Grid(" ",1)
  def newPlayers(): IPlayers = Players()
  def newBag(): IBag = Bag(random = false)
}
