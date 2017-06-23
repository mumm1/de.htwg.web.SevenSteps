package de.htwg.se.SevenSteps.model.grid.mockImpl

import de.htwg.se.SevenSteps.model.grid.{ICell, IGrid}
import de.htwg.se.SevenSteps.model.grid.basicImpl.Cell

import scala.util._

case class GridMock() extends IGrid {
  def set(row: Int, col: Int, color: Char): IGrid = this
  def set(row: Int, col: Int, height: Int): IGrid = this
  def cell(row: Int, col: Int): Try[ICell] = Success(new CellMock)
  def nonEmpty: Boolean = false
  def getColors: List[Char] = List('b','b','b','b')
  def getHeights: List[Int] = List(0,2,2,2)
  def cellsToString(): String = ""
  def getColorsWithHeight0: List[Char] = List('b')
  def rows: Int = 2
  def cols: Int = 2
  def resetHeights: IGrid = this
  def grid: Vector[Cell] = Vector()
}

