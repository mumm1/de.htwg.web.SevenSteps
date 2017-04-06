package de.htwg.se.SevenSteps.model

case class Grid(rows: Int,cols: Int,cells: Option[Vector[Cell]] = None) {

  val grid = cells getOrElse Vector.fill(rows*cols)(new Cell)

  
  private def getIndex(row: Int,col: Int): Int= {cols*row+col}
  def cell(row: Int,col:Int):Cell={grid(getIndex(row,col))}
  override def toString = {
    val linesep = "+---"*cols+"+\n"
    val line = "|XXX"*cols+"|\n"
    var strGrid = "\n"+(linesep+line)*rows+linesep
    for (row <- 0 until rows;
         col <- 0 until cols){
      strGrid = strGrid.replaceFirst("XXX", grid(getIndex(row,col)).toString)
    }
    strGrid
  }
  def set(row: Int, col: Int,color: String):Grid={copy(cells=Option(grid.updated(getIndex(row,col),cell(row,col).copy(color=color  ))))}
  def set(row: Int, col: Int,height: Int):  Grid={copy(cells=Option(grid.updated(getIndex(row,col),cell(row,col).copy(height=height))))}
  
}

