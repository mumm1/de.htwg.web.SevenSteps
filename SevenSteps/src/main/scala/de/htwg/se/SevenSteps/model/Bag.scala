package de.htwg.se.SevenSteps.model

import scala.collection.mutable.ListMap
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer


import scala.util.Random


case class Bag (name:String, stones:ListMap[Char,Int] = ListMap[Char,Int]()){

	val colors =Array ('a','b','c','d','e','f')
			for ((c)<- colors )
				stones += c -> 20   

				/*
				def pull (num: Int): List[Char]={
						var list:ListBuffer[Char]=ListBuffer()
								for( i <- 0 to num){
									val ran = Random.nextInt(6)
											val color =  colors.apply(ran)								
											stones(color) =stones.apply(color) -1
											list += color
								}
				list.toList
				}
				 */

				def pull () :Char={
						val ran = Random.nextInt(6)
						val color =  colors.apply(ran)								
						stones(color) =stones.apply(color) -1

						color
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