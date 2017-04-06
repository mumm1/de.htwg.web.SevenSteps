package de.htwg.se.SevenSteps.model

case class Grid(rows: Int,cols: Int,cells: Option[Vector[Cell]] = None) {

  val grid = cells getOrElse Vector.fill(rows*cols)(new Cell)

  
  private def getIndex(row: Int,col: Int): Int= {cols*row+col}
  def getCell(row: Int,col:Int):Cell={grid(getIndex(row,col))}
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
  def setColor(row: Int, col: Int, color: String):Grid={copy(cells=Option(grid.updated(getIndex(row,col), new Cell(color,0))))}

}

