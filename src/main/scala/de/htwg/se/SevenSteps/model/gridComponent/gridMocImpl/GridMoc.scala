package de.htwg.se.SevenSteps.model.gridComponent.gridMocImpl

import de.htwg.se.SevenSteps.model.gridComponent.{ICell, IGrid}
import scala.util._

case class GridMoc() extends IGrid {
  def set(row: Int, col: Int, color: Char): IGrid = this
  def set(row: Int, col: Int, height: Int): IGrid = this
  def cell(row: Int, col: Int): Try[ICell] = Success(new CellMoc)
  def nonEmpty: Boolean = false
  def getColors: List[Char] = List()
  def getHeights: List[Int] = List()
  def cellsToString(): String = ""
  def getColorsWithHeight0: List[Char] = List()
  def rows: Int = 2
  def cols: Int = 2
}

