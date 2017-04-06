package de.htwg.se.SevenSteps.model

object PlayerWS {
  
  var p1 = new Player("Jsjndekdlus" , 0)          //> p1  : de.htwg.se.SevenSteps.model.Player = yellow --> 0
                                                  //| green --> 33
                                                  //| white --> 0
                                                  //| red --> 0
                                                  //| blue --> 0
                                                  //| Some(33)
   p1.add_stone("white",7)
 
 p1.toString                                      //> res0: String = yellow --> 0
                                                  //| green --> 33
                                                  //| white --> 7
                                                  //| red --> 0
                                                  //| blue --> 0
                                                  //| Some(33)
 
}