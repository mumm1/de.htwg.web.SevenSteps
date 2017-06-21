package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.IGridFactory
import de.htwg.se.SevenSteps.model.player.IPlayers

trait Factory extends IGridFactory {
  def newPlayers(): IPlayers
  def newBag(): IBag
  def newController: IController
}
