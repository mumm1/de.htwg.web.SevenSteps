package de.htwg.se.SevenSteps.controller
import de.htwg.se.SevenSteps.model._
import scala.collection.mutable.Stack

object ControllerWS {
  var con = new Controller()                      //> con  : de#27.htwg#17785.se#17787.SevenSteps#17789.controller#17799.Controlle
                                                  //| r#156737 = 
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| 
  con.exploreCommand(new AddPlayer("Hans"))       //> Added Player: Hans: g=0, b=0, a=0, c=0, Points=0
  con.exploreCommand(new AddPlayer("PeteRrs"))    //> Added Player: PeteRrs: g=0, b=0, a=0, c=0, Points=0
  con.toString                                    //> res0: String#240 = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //|    PeteRrs: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| "
  con.undo()
  con.toString                                    //> res1: String#240 = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| "
  con.undo()
  con.toString()                                  //> res2: String#240 = "
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| "
  con.redo()
  con.redo()
  con.redo()
  con.toString()                                  //> res3: String#240 = "
                                                  //|    Hans: g=0, b=0, a=0, c=0, Points=0
                                                  //|    PeteRrs: g=0, b=0, a=0, c=0, Points=0
                                                  //| 
                                                  //| +---+
                                                  //| |   |
                                                  //| +---+
                                                  //| "
}