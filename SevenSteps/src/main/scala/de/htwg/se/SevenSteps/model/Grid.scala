package de.htwg.se.SevenSteps.model

case class Grid(rows: Int,cols: Int) {
  
  val grid=Array.ofDim[Cell](rows, cols)
  
}

