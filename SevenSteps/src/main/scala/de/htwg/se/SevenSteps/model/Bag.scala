package de.htwg.se.SevenSteps.model

import scala.collection.mutable.ListMap
import scala.util.Random


class Bag {

	val colors =Array ('a','b','c','d','e','f')

			val bag = ListMap[Char,Int]()  
			for ((c)<- colors )
				bag += c -> 20   





				def pull (): Char={

						val ran = Random.nextInt(6)
								val color =  colors.apply(ran)								
								bag(color) =bag.apply(color) -1
								color

				}

				override def toString = {	  
						val sb = new StringBuilder
								sb.append("\n")
								for ((k,v) <- bag){ sb.append(k + " --> " + v + "\n")}
						sb.toString()
				}



}