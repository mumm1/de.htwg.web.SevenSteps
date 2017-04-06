package de.htwg.se.SevenSteps.model

case class Player (name : String, points : Int) {

  var colors =Array {'a';'b';'c'}
  
	val map = collection.mutable.Map[Char, Int]()

  for ((c) <- colors){
    map += (c -> 0)
    
  }
  
  



			def add_stone (color: String, number : Int){ map(color) = map.getOrElse(color, 999) + number}
	def add_stone(color: String )


	override def toString = {
			val sb = new StringBuilder
					for ((k,v) <- map){ sb.append(k + " --> " + v + "\n")}
			sb.append(map get "green")
			sb.toString()

	}
}