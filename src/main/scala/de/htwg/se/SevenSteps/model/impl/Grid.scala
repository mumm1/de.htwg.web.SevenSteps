package de.htwg.se.SevenSteps.model.impl

import scala.collection.mutable.ListBuffer
import scala.util._

case class Grid(rows: Int, cols: Int, cells: Option[Vector[Cell]] = None) {
  val grid: Vector[Cell] = cells getOrElse Vector.fill(rows * cols)(new Cell)
  def nonEmpty: Boolean = {
    cellsToString().replaceAll(" ", "").nonEmpty
  }
  def cellsToString(): String = {
    var text = ""
    grid.foreach(cell => text += cell.color.toString)
    text
  }
  def this(colors: String, cols: Int) = {
    this(math.ceil(colors.length() / cols.toFloat).toInt, cols,
      cells = {
        val empty = " " * (math.ceil(colors.length() / cols.toFloat).toInt * cols - colors.length())
        val cCells = for (c <- (colors + empty).toList) yield {
          Cell(c.toChar)
        }
        Option(cCells.toVector)
      })
  }
  override def toString: String = {
    if (grid.isEmpty) {
      "\n"
    } else {
      val linesep = "+---" * cols + "+\n"
      val line = "|XXX" * cols + "|\n"
      var strGrid = "\n" + (linesep + line) * rows + linesep
      for (
        row <- 0 until rows;
        col <- 0 until cols
      ) {
        strGrid = strGrid.replaceFirst("XXX", grid(getIndex(row, col)).toString)
      }
      strGrid
    }
  }
  private def getIndex(row: Int, col: Int): Int = {
    if (row >= rows || col >= cols) {
      -1
    } else {
      cols * row + col
    }
  }
  def set(row: Int, col: Int, color: Char): Grid = {
    copy(cells = Option(grid.updated(getIndex(row, col), cell(row, col).get.copy(color = color))))
  }
  def set(row: Int, col: Int, height: Int): Grid = {
    copy(cells = Option(grid.updated(getIndex(row, col), cell(row, col).get.copy(height = height))))
  }
  def cell(row: Int, col: Int): Try[Cell] = {
    try {
      Success(grid(getIndex(row, col)))
    } catch {
      case e: IndexOutOfBoundsException => Failure(e)
    }
  }
  def getColors: List[Char] = {
    var list: ListBuffer[Char] = ListBuffer()
    cellsToString().foreach(c => if (!list.contains(c) & c != ' ') {
      list += c
    })
    list.toList
  }
  def getHeights: List[Int] = {
    var list: ListBuffer[Int] = ListBuffer()
    grid.foreach(cell => list += cell.height)
    list.toList
  }
}

