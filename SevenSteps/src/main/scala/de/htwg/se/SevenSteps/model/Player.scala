package de.htwg.se.SevenSteps.model

case class Player (name : String, points : Int) {

	private val colors =Array ('a','b','c','g')

	 val map = collection.mutable.ListMap[Char, Int]()

			for ((c) <- colors){  
				map += (c -> 0)
			}




//	def add_stone (color: Char, number : Int){ map(color) = map.getOrElse(color, 999) + number}
	//	def add_stone(color: Char ,place : Int ){ }


	override def toString = {
			val sb = new StringBuilder
					for ((k,v) <- map){ sb.append(k + " --> " + v + "\n")}
			sb.toString()

	}
}