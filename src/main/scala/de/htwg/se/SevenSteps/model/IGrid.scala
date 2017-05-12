package de.htwg.se.SevenSteps.model

import de.htwg.se.SevenSteps.model.impl.Cell
import scala.util.Try

trait IGrid {
  def set(row: Int, col: Int, color: Char): IGrid
  def set(row: Int, col: Int, height: Int): IGrid
  def cell(row: Int, col: Int): Try[Cell]
  def nonEmpty: Boolean
  def getColors: List[Char]
  def getHeights: List[Int]
  def cellsToString(): String
}
