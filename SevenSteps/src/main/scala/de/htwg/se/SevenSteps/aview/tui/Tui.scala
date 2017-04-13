package de.htwg.se.SevennSteps.aview.tui

import de.htwg.se.SevenSteps.controller._

class Tui(var con: Controller){
  printTui
  def update = printTui
  def printTui = {
    println("\n############ SevenSteps ############")
    println(con.toString)
    println("####################################\n")
    println("Enter command: q-Quit, u-Undo, r-Redo, a-AddPlayer [Name], n-NewGrid [ColorString] [ColsInt], s-StartGame")
    
  }
  def processInputLine(input: String) = {
    var continue = true
    input match {
      case "q" => continue = false
      case "s" => con.exploreCommand(new StartGame())
      case "u" => con.undo()
      case "r" => con.redo()
      case _ => {
        input.split(" ").toList match {
          case "a" :: player :: Nil          => con.exploreCommand(new AddPlayer(player))
          case "n" :: colors :: cols:: Nil   => con.exploreCommand(new NewGrid(colors.replace('-', ' '),cols.toInt))
          case _                             => println("False Input!!!")
        }
      }
    }
    update
    continue
  }
}
