package de.htwg.se.SevenSteps.model.grid.basicImpl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.model.grid.IGrid

import scala.collection.mutable.ListBuffer
import scala.util._

case class Grid @JsonCreator()(rows: Int, cols: Int, grid: Vector[Cell]) extends IGrid {
  //def this(rows: Int, cols: Int) = this(rows: Int, cols: Int,Vector.fill(rows * cols)(new Cell))
  //def this() = this(1,1,Vector(Cell()))
  def getColorsWithHeight0: List[Char] = {
    var result: ListBuffer[Char] = ListBuffer()
    for (cell <- grid) {
      if (cell.height == 0 && cell.color != ' ' && !result.contains(cell.color)) {
        result += cell.color
      }
    }
    result.toList
  }
  def nonEmpty: Boolean = {
    cellsToString().replaceAll(" ", "").nonEmpty
  }
  def cellsToString(): String = {
    var text = ""
    grid.foreach(cell => text += cell.color.toString)
    text
  }
  override def toString: String = {
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
  private def getIndex(row: Int, col: Int): Int = {
    if (row >= rows || col >= cols) {
      -1
    } else {
      cols * row + col
    }
  }
  def set(row: Int, col: Int, color: Char): Grid = {
    copy(grid = grid.updated(getIndex(row, col), cell(row, col).get.copy(color = color)))
  }
  def cell(row: Int, col: Int): Try[Cell] = {
    try {
      Success(grid(getIndex(row, col)))
    } catch {
      case e: IndexOutOfBoundsException => Failure(e)
    }
  }
  def set(row: Int, col: Int, height: Int): Grid = {
    copy(grid = grid.updated(getIndex(row, col), cell(row, col).get.copy(height = height)))
  }
  def getColors: List[Char] = {
    var list: ListBuffer[Char] = ListBuffer()
    cellsToString().foreach(c => if (!list.contains(c) & c != ' ') {
      list += c
    })
    list.toList
  }
  def toXML(): scala.xml.Elem = {
    <grid rows={rows.toString} col={cols.toString}>
      {grid.map { entry =>
      val cell = entry
      <cellen celll={cell.toXML()}></cellen>
    }}
    </grid>
  }
  def getHeights: List[Int] = {
    var list: ListBuffer[Int] = ListBuffer()
    grid.foreach(cell => list += cell.height)
    list.toList
  }
  def resetHeights: Grid = new Grid(this.cellsToString(),this.cols)
  @Inject()
  def this(@Assisted() colors: String, @Assisted() cols: Int) = {
    this(math.ceil(colors.length() / cols.toFloat).toInt, cols,
      grid = {
        val empty = " " * (math.ceil(colors.length() / cols.toFloat).toInt * cols - colors.length())
        val cCells = for (c <- (colors + empty).toList) yield {
          Cell(c.toChar)
        }
        cCells.toVector
      })
  }
}

