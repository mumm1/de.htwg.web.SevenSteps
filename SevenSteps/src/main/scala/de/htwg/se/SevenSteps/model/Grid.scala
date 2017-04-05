package de.htwg.se.SevenSteps.model

case class Grid(rows: Int,cols: Int) {
  
  val grid=Array.ofDim[Cell](rows, cols)
  
  override def toString = {
    val linesep = "+---"*cols+"+\n"
    val line = "|XXX"*cols+"|\n"
    var strGrid = "\n"+(linesep+line)*rows+linesep
    for (row <- 0 until rows;
         col <- 0 until cols){
      strGrid = strGrid.replaceFirst("XXX", "   ")
    }
    strGrid
  }
  
}

