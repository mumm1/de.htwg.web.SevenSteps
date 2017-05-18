package de.htwg.se.SevenSteps.model

import de.htwg.se.SevenSteps.model.impl.{Grid, Players}

trait ModelFactory {
  def newGrid(colors: String, cols: Int): IGrid
  def newGrid(): IGrid
  def newPlayers(): IPlayers
}

object ModelFactory1 extends ModelFactory {
  def newGrid(colors: String, cols: Int): IGrid = new Grid(colors, cols)
  def newGrid(): IGrid = Grid(0, 0)
  def newPlayers(): IPlayers = Players()
}