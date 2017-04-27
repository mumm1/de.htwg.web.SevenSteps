
package de.htwg.se.SevenSteps.model

import scala.collection.mutable
import scala.util.Random

case class Bag(name: String, stones: mutable.ListMap[Char, Int] = mutable.ListMap[Char, Int](), colors: List[Char] = List[Char]()) {
  def fillup(): Unit = {
    for ((c) <- colors)
      stones += c -> 20
  }
  def pull(): Char = {
    if (! colors.isEmpty){
    val ran = Random.nextInt(colors.length)
    val color: Char = colors.apply(ran)
    stones(color) = stones.apply(color) - 1
    return color
    }
    '$'
  }
  
  
  /*
  override def toString = {
      val sb = new StringBuilder
          sb.append("\n")
          for ((k,v) <- stones){ sb.append(k + " --> " + v + "\n")}
      sb.toString()
  }

   */
}