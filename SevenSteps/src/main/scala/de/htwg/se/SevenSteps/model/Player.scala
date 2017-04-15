package de.htwg.se.SevenSteps.model
import collection.mutable.Map

case class Player (name : String, points : Int=0,val map:Map[Char, Int]=Map[Char, Int]()) {

	def setColors(colors:List[Char]){
	  map.clear()
		for ((c) <- colors){  
			map += (c -> 3)
		}	  
	}
	def setColor(color:Char,value:Int):Player={copy(map=map.updated(color, value))}

//	def add_stone (color: Char, number : Int){ map(color) = map.getOrElse(color, 999) + number}
	//	def add_stone(color: Char ,place : Int ){ }


	override def toString = {
			val sb = new StringBuilder
			sb.append(name+": ")
					for ((k,v) <- map){ sb.append(k + "=" + v + ", ")}
			sb.append("Points="+points)
			sb.toString()

	}
}