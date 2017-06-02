package de.htwg.se.SevenSteps.model.gridComponent

import scala.util.Try

trait IGrid {
  def set(row: Int, col: Int, color: Char): IGrid
  def set(row: Int, col: Int, height: Int): IGrid
  def cell(row: Int, col: Int): Try[ICell]
  def nonEmpty: Boolean
  def getColors: List[Char]
  def getHeights: List[Int]
  def cellsToString(): String
  def getColorsWithHeight0: List[Char]
  def rows: Int
  def cols: Int
}
