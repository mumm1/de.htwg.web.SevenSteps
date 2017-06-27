package de.htwg.se.SevenSteps.model.grid.basicImpl

import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.model.grid.ICell

case class Cell @JsonCreator()(color: Char = ' ', height: Int = 0) extends ICell {
  override def toString: String = {
    if (color != ' ') {
      color.toString + " " + height
    } else {
      "   "
    }
  }
  def toXML(): scala.xml.Elem = {
    <cell colorCell={color.toString} height={height.toString}></cell>
  }
}

