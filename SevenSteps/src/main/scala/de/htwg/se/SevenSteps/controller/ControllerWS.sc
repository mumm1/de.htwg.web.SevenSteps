package de.htwg.se.SevenSteps.controller
import de.htwg.se.SevenSteps.model._
import scala.collection.mutable.Stack

object ControllerWS {
  var con = new Controller()                      //> con  : de.htwg.se.SevenSteps.controller.Controller = 
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| 
  con.exploreCommand(new AddPlayer("Hans"))       //> Added Player: Hans: g=0, b=0, a=0, c=0, Points=0
  con.exploreCommand(new AddPlayer("PeteRrs"))    //> Added Player: PeteRrs: g=0, b=0, a=0, c=0, Points=0
  con.toString                                    //> res0: String = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //|    PeteRrs: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| "
  con.undo()
  con.toString                                    //> res1: String = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| "
  con.undo()
  con.toString()                                  //> res2: String = "
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| "
}