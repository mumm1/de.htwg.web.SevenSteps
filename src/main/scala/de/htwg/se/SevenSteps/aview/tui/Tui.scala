package de.htwg.se.SevenSteps.aview.tui

import de.htwg.se.SevenSteps.controller._
import de.htwg.se.SevenSteps.util.Observer

class Tui(var con: IController) extends Observer {
  con.add(this)
  val DEFAULT_GRID_COLS = 5
  printTui()
  def processInputLine(input: String): Boolean = {
    var continue = true
    input match {
      case "q" => continue = false
      case "s" => con.startGame()
      case "n" => con.nextPlayer()
      case "u" => con.undo()
      case "r" => con.redo()
      case "t" => con.newGame()
      case _ => processMoreParameter(input)
    }
    continue
  }
  private def processMoreParameter(input: String): Unit = {
    input.split(" ").toList match {
      case "a" :: player :: Nil => con.addPlayer(player)
      case "g" :: colors :: cols :: Nil => con.newGrid(colors.replace('-', ' '), str2Int(cols, DEFAULT_GRID_COLS))
      case row :: col :: Nil => con.setStone(str2Int(row, -1), str2Int(col, -1))
      case _ => con.state.message = "False Input!!!"
    }
  }
  def str2Int(s: String, default: Int): Int = {
    try {
      s.toInt
    } catch {
      case _: Exception => default
    }
  }
  override def update(): Unit = printTui()
  def printTui(): Unit = {
    //    val c: Char=0x1B
    //    print("%c[%d;%df".format(,c,10,10))
    println(generateTuiText)
  }
  def generateTuiText: String = {
    var result = "\n" + con.toString + "\nEnter command: q-Quit, u-Undo, r-Redo"
    if (con.state.gameState.isInstanceOf[IPrepare]) {
      result+="\n               a-AddPlayer [Name], g-Grid [ColorString] [ColsInt], s-StartGame"
    }
    if (con.state.gameState.isInstanceOf[IPlay]) {
      result+="\n               SetStone [row] [col], n-NextPlayer"
    }
    if (con.state.gameState.isInstanceOf[IFinish]) {
      result+="\n               t-restart Game"
    }
    result
  }
}
