
package de.htwg.se.SevenSteps.model

import scala.collection.mutable.ListMap
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer


import scala.util.Random


case class Bag (name:String, stones:ListMap[Char,Int] = ListMap[Char,Int](), test:List[Char] = List[Char]()){
  
 
  def fillup(){
			for ((c)<- test )
				stones += c -> 20   
  }
				
				def pull2 (): Char={
				  println("-----------" + test)
				  println(stones)
									val ran = Random.nextInt(test.length)
											val color : Char = test.apply(ran)
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