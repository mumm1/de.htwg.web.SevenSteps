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
  grid=grid.setColor(0,0,"c").setColor(0,2,"c")
  grid                                            //> res1: de.htwg.se.SevenSteps.model.Grid = 
                                                  //| +---+---+---+---+---+
                                                  //| |c 0|   |c 0|   |   |
                                                  //| +---+---+---+---+---+
                                                  //| |   |   |   |   |   |
                                                  //| +---+---+---+---+---+
                                                  //| 
  grid=grid.setColor(1,2,"c")
  grid                                            //> res2: de.htwg.se.SevenSteps.model.Grid = 
                                                  //| +---+---+---+---+---+
                                                  //| |c 0|   |c 0|   |   |
                                                  //| +---+---+---+---+---+
                                                  //| |   |   |c 0|   |   |
                                                  //| +---+---+---+---+---+
                                                  //| 
}