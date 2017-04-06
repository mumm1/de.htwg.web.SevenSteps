package de.htwg.se.SevenSteps.model

case class Cell(color: String=" ",height: Int=0) {
  override def toString = {if (color!=" "){color.charAt(0).toString()+" "+height}else{"   "}}
}

