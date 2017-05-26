package de.htwg.se.SevenSteps.model

import de.htwg.se.SevenSteps.model.impl.{Grid, Players, Bag}

trait ModelFactory {
  def newGrid(colors: String, cols: Int): IGrid
  def newGrid(): IGrid
  def newPlayers(): IPlayers
  def newBag(): IBag
}

object ModelFactory1 extends ModelFactory {
  def newGrid(colors: String, cols: Int): IGrid = new Grid(colors, cols)
  def newGrid(): IGrid = new Grid("aaabbba  ab a bababb abb ba aab",10)
  def newPlayers(): IPlayers = Players()
  def newBag(): IBag = Bag(random = false)
}
