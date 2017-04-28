
package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._
import de.htwg.se.SevenSteps.util.Observable

import scala.collection.mutable
import scala.util._

case class Controller(var grid: Grid = Grid(0, 0)) extends Observable {
  var bag: Bag = Bag("Bag")
  var curHeight: Int = 0
  var players = Players()
  var gameState: GameState = Prepare(this)
  var lastCells: mutable.Stack[(Int, Int)] = mutable.Stack()
  var undoStack: mutable.Stack[Command] = mutable.Stack()
  var redoStack: mutable.Stack[Command] = mutable.Stack()
  var message = "Welcome to SevenSteps"
  def prepareNewPlayer(): Unit = {
    for (_ <- getCurPlayer.getStoneNumber to 6) {
      players = players.updateCurPlayer(players.getCurPlayer.incColor(bag.pull(), 1))
    }
    curHeight = 0
    lastCells.clear()
  }
  def getCurPlayer: Player = {
    players.getCurPlayer
  }
  def addPlayer(name: String): Try[String] = doIt(AddPlayer(name))
  def newGrid(colors: String, cols: Int): Try[String] = doIt(NewGrid(colors, cols))
  def doIt(com: Command): Try[String] = {
    val explored = gameState.exploreCommand(com)
    explored match {
      case Success(s) => {
        undoStack.push(com)
        redoStack.clear()
        message = s
      }
      case Failure(e) => message = e.getMessage
    }
    notifyObservers()
    explored
  }
  def startGame(): Try[String] = doIt(StartGame())
  def nextPlayer(): Try[String] = doIt(NextPlayer())
  def setStone(row: Int, col: Int): Try[String] = doIt(SetStone(row, col))
  def undo(): Try[String] = {
    if (undoStack.nonEmpty) {
      val temp = undoStack.pop()
      val temp2 = temp.undo(this)
      temp2 match {
        case Success(s) => {
          message = "Undo: " + s
          redoStack.push(temp)
        }
        case Failure(e) => message = e.getMessage
      }
      notifyObservers()
      temp2
    }
    else {
      message = "Can't undo now!"
      Failure(new Exception(message))
    }
  }
  def redo(): Try[String] = {
    if (redoStack.nonEmpty) {
      val temp = redoStack.pop()
      val temp2 = temp.doIt(this)
      temp2 match {
        case Success(s) => {
          message = "Redo: " + s
          undoStack.push(temp)
        }
        case Failure(e) => message = e.getMessage
      }
      notifyObservers()
      temp2
    }
    else {
      message = "Can't redo now!"
      Failure(new Exception(message))
    }
  }
  override def toString: String = {
    val text = "\n############  " + message + "  ############\n\n"
    val len = text.length()
    text + players.toString() + grid.toString() + "#" * (len - 2) + "\n"
  }
}

// ##################### Controller Commands #######################
trait Command {
  def doIt(c: Controller): Try[String]
  def undo(c: Controller): Try[String]
}

case class AddPlayer(name: String) extends Command {
  val player = Player(name)
  override def doIt(c: Controller): Success[String] = {
    c.players = c.players.push(player)
    Success("Added Player " + name)
  }
  override def undo(c: Controller): Try[String] = {
    c.players = c.players.pop()
    Success("Deleted Player")
  }
}

case class NewGrid(colors: String, cols: Int) extends Command {
  var oldGrid: Grid = _
  override def doIt(c: Controller): Try[String] = {
    oldGrid = c.grid
    c.grid = new Grid(colors, cols)
    Success("Build new Grid")
  }
  override def undo(c: Controller): Try[String] = {
    c.grid = oldGrid
    Success("Deleted new Grid")
  }
}

case class StartGame() extends Command {
  override def doIt(c: Controller): Try[String] = {
    if (c.players.nonEmpty && c.grid.nonEmpty) {
      c.gameState = Play(c)
      val colors = c.grid.getColors
      c.players = c.players.setColors(colors)
      c.bag = c.bag.copy(colors = colors)
      c.bag.fillup()
      c.prepareNewPlayer()
      Success("Started the game")
    }
    else {
      Failure(new Exception("Can't start the game: Not enough Players"))
    }
  }
  override def undo(c: Controller): Try[String] = {
    c.undoStack.clear()
    Failure(new Exception("You can't undo the start of the game!"))
  }
}

case class NextPlayer() extends Command {
  override def doIt(c: Controller): Try[String] = {
    c.players = c.players.next()
    c.prepareNewPlayer()
    Success("Player " + c.getCurPlayer.name + " it is your turn!")
  }
  override def undo(c: Controller): Try[String] = {
    c.undoStack.clear()
    Failure(new Exception("You can't undo to previous player!"))
  }
}

case class SetStone(row: Int, col: Int) extends Command {
  override def doIt(c: Controller): Try[String] = {
    c.grid.cell(row, col) match {
      case Failure(_) => Failure(new Exception("You have to set a Stone on height " + (c.curHeight - 1) + " or " + c.curHeight))
      case Success(cell) =>
        if (c.lastCells.nonEmpty) {
          if (!isNeighbour(row, col, c)) {
            return Failure(new Exception("You have to set a Stone neighboring to the last Stone!"))
          }
        }
        if (c.lastCells.contains((row, col))) {
          return Failure(new Exception("You can't set on a cell twice!"))
        }
        c.getCurPlayer.map.get(cell.color) match {
          case None => Failure(new Exception("You can't place here!"))
          case Some(0) => Failure(new Exception("You don't have Stones from color '" + cell.color + "'"))
          case Some(_) =>
            val dif = c.curHeight - cell.height
            if (dif == 0 | dif == 1) {
              c.grid = c.grid.set(row, col, cell.height + 1)
              c.players = c.players.updateCurPlayer(c.players.getCurPlayer.incPoints(cell.height + 1).incColor(cell.color, -1))
              c.curHeight = cell.height + 1
              c.lastCells.push((row, col))
              Success("You set a stone")
            }
            else {
              Failure(new Exception("You have to set a Stone on height " + (c.curHeight - 1) + " or " + c.curHeight))
            }
        }
    }
  }
  def isNeighbour(row: Int, col: Int, c: Controller): Boolean = (math.abs(row - c.lastCells.head._1) + math.abs(col - c.lastCells.head._2)) == 1
  override def undo(c: Controller): Try[String] = {
    val cell = c.grid.cell(row, col).get
    c.grid = c.grid.set(row, col, cell.height - 1)
    c.players = c.players.updateCurPlayer(c.players.getCurPlayer.incPoints(-cell.height).incColor(cell.color, +1))
    c.curHeight = cell.height - 1
    c.lastCells.pop()
    Success("Take the Stone")
  }
}

// ##################### Finite State Machine #######################
trait GameState {
  def exploreCommand(com: Command): Try[String]
}

case class Prepare(c: Controller) extends GameState {
  override def exploreCommand(com: Command): Try[String] = {
    com match {
      case command: AddPlayer => command.doIt(c)
      case command: NewGrid => command.doIt(c)
      case command: StartGame => command.doIt(c)
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}

case class Play(c: Controller) extends GameState {
  override def exploreCommand(com: Command): Try[String] = {
    com match {
      case command: NextPlayer => command.doIt(c)
      case command: SetStone => command.doIt(c)
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}


