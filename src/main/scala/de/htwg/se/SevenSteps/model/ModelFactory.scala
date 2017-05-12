package de.htwg.se.SevenSteps.model

import de.htwg.se.SevenSteps.model.impl.Grid

trait ModelFactory {
  def newGrid(colors: String, cols: Int): IGrid
}

object ModelFactory1 extends ModelFactory {
  def newGrid(colors: String, cols: Int): IGrid = {
    new Grid(colors, cols)
  }
  //  def newCell(colors: String, cols: Int): ICell = {
  //    new Cell(colors,cols)
  //  }
}