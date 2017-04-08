package de.htwg.se.SevenSteps.model

import de.htwg.se.SevenSteps.controller._

object GridWS {
  println("Welcome to the Scala worksheeest")     //> Welcome to the Scala worksheeest
	var grid = new Grid(2,5)                  //> grid  : de.htwg.se.SevenSteps.model.Grid = 
                                                  //| +---+---+---+---+---+
                                                  //| |   |   |   |   |   |
                                                  //| +---+---+---+---+---+
                                                  //| |   |   |   |   |   |
                                                  //| +---+---+---+---+---+
                                                  //| 
	var colors="aaa bbb ccc".split(" ")       //> colors  : Array[String] = Array(aaa, bbb, ccc)
	var c="hello world".split(" ")            //> c  : Array[String] = Array(hello, world)
	for(x <- "abc".toList) yield {x+"l"}      //> res0: List[String] = List(al, bl, cl)
	
	new Grid("aab zwio pokwdc",4)             //> res1: de.htwg.se.SevenSteps.model.Grid = 
                                                  //| +---+---+---+---+
                                                  //| |a 0|a 0|b 0|   |
                                                  //| +---+---+---+---+
                                                  //| |z 0|w 0|i 0|o 0|
                                                  //| +---+---+---+---+
                                                  //| |   |p 0|o 0|k 0|
                                                  //| +---+---+---+---+
                                                  //| |w 0|d 0|c 0|   |
                                                  //| +---+---+---+---+
                                                  //| 
  var con = new Controller(new Grid("aabb",2))    //> con  : de.htwg.se.SevenSteps.controller.Controller = 
                                                  //| 
                                                  //| +---+---+
                                                  //| |a 0|a 0|
                                                  //| +---+---+
                                                  //| |b 0|b 0|
                                                  //| +---+---+
                                                  //| 
  con.addPlayer(new Player("Hans"))
  con.addPlayer(new Player("Peter"))
  con.toString                                    //> res2: String = "
                                                  //| -> Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //|    Peter: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+---+
                                                  //| |a 0|a 0|
                                                  //| +---+---+
                                                  //| |b 0|b 0|
                                                  //| +---+---+
                                                  //| "
}