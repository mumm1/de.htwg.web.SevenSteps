package de.htwg.se.SevenSteps.model

object GridWS {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  var grid = new Grid(2,5)                        //> grid  : de.htwg.se.SevenSteps.model.Grid = 
                                                  //| +---+---+---+---+---+
                                                  //| |   |   |   |   |   |
                                                  //| +---+---+---+---+---+
                                                  //| |   |   |   |   |   |
                                                  //| +---+---+---+---+---+
                                                  //| 
                                                
  grid.rows                                       //> res0: Int = 2
  grid=grid.set(0,0,'c').set(0,2,'c')
  grid                                            //> res1: de.htwg.se.SevenSteps.model.Grid = 
                                                  //| +---+---+---+---+---+
                                                  //| |c 0|   |c 0|   |   |
                                                  //| +---+---+---+---+---+
                                                  //| |   |   |   |   |   |
                                                  //| +---+---+---+---+---+
                                                  //| 
  grid=grid.set(1,2,'A').set(1,2,3)
  grid                                            //> res2: de.htwg.se.SevenSteps.model.Grid = 
                                                  //| +---+---+---+---+---+
                                                  //| |c 0|   |c 0|   |   |
                                                  //| +---+---+---+---+---+
                                                  //| |   |   |A 3|   |   |
                                                  //| +---+---+---+---+---+
                                                  //| 
  val cell = new Cell('b',2)                      //> cell  : de.htwg.se.SevenSteps.model.Cell = b 2
}