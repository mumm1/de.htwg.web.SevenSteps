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
  con.exploreCommand(new AddPlayer("Hans"))       //> res0: scala.util.Try[String] = Success(Added Player: Hans: g=0, b=0, a=0, c=
                                                  //| 0, Points=0)
  con.exploreCommand(new AddPlayer("PetehR"))     //> res1: scala.util.Try[String] = Success(Added Player: PetehR: g=0, b=0, a=0, 
                                                  //| c=0, Points=0)
  con.exploreCommand(new NewGrid("ab sdd",3))     //> res2: scala.util.Try[String] = Success(Build new Grid)
  con.toString                                    //> res3: String = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //|    PetehR: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+---+---+
                                                  //| |a 0|b 0|   |
                                                  //| +---+---+---+
                                                  //| |s 0|d 0|d 0|
                                                  //| +---+---+---+
                                                  //| "
  con.undo()
  con.toString                                    //> res4: String = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //|    PetehR: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| "
  con.undo()
  con.toString()                                  //> res5: String = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| "
  con.redo()
  con.redo()
  con.redo()
  con.toString()                                  //> res6: String = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //|    PetehR: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+---+---+
                                                  //| |a 0|b 0|   |
                                                  //| +---+---+---+
                                                  //| |s 0|d 0|d 0|
                                                  //| +---+---+---+
                                                  //| "
  con.exploreCommand(new NewGrid("ab sdascgd",3)) //> res7: scala.util.Try[String] = Success(Build new Grid)
  con.toString()                                  //> res8: String = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //|    PetehR: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+---+---+
                                                  //| |a 0|b 0|   |
                                                  //| +---+---+---+
                                                  //| |s 0|d 0|a 0|
                                                  //| +---+---+---+
                                                  //| |s 0|c 0|g 0|
                                                  //| +---+---+---+
                                                  //| |d 0|   |   |
                                                  //| +---+---+---+
                                                  //| "
}