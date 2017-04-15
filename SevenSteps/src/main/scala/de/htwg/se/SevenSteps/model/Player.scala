package de.htwg.se.SevenSteps.model

case class Player (name : String, points : Int=0) {

	val map = collection.mutable.ListMap[Char, Int]()
	def setColors(colors:List[Char]){
	  map.clear()
		for ((c) <- colors){  
			map += (c -> 0)
		}	  
	}





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