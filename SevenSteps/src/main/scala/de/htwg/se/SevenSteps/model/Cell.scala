package de.htwg.se.SevenSteps.model

case class Cell(color: Char=' ',height: Int=0) {
  override def toString = {if (color!=' '){color.toString()+" "+height}else{"   "}}
}

