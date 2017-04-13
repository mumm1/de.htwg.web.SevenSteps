package de.htwg.se.SevennSteps.aview.tui

import de.htwg.se.SevenSteps.controller._

class Tui(var con: Controller){
  printTui
  def update = printTui
  def printTui = {
    println("\n"*30)
    println(con.toString)
    println("Enter command: q-Quit, u-Undo, r-Redo")
    if (con.gameState.isInstanceOf[Prepare])
      println("               a-AddPlayer [Name], g-Grid [ColorString] [ColsInt], s-StartGame")  
    if (con.gameState.isInstanceOf[Play])
      println("               SetStone [row] [col], n-NextPlayer")  

    
  }
  def Str2Int(s:String,default:Int):Int={try{s.toInt}catch{ case e:Exception => default}}
  def processInputLine(input: String) = {
    var continue = true
    input match {
      case "q" => continue = false
      case "s" => con.exploreCommand(new StartGame())
      case "n" => con.exploreCommand(new NextPlayer())
      case "u" => con.undo()
      case "r" => con.redo()
      case _ => {
        input.split(" ").toList match {
          case "a" :: player :: Nil          => con.exploreCommand(new AddPlayer(player))
          case "g" :: colors :: cols:: Nil   => con.exploreCommand(new NewGrid(colors.replace('-', ' '),Str2Int(cols,5)))
          case row :: col:: Nil              => con.exploreCommand(new SetStonde(Str2Int(row,-1),Str2Int(col,-1)))
          case _                             => con.message="False Input!!!"
        }
      }
    }
    update
    continue
  }
}
