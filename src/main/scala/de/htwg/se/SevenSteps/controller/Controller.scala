
package de.htwg.se.SevenSteps.controller

import de.htwg.se.SevenSteps.model._
import de.htwg.se.SevenSteps.util.Observable

import scala.collection.mutable
import scala.util._

case class Controller(var grid: Grid = Grid(0, 0),
                      var bag: Bag = Bag("Bag"),
                      var curHeight: Int = 0,
                      var players: Players = Players(),
                      var lastCells: mutable.Stack[(Int, Int)] = mutable.Stack(),
                      var undoStack: mutable.Stack[Command] = mutable.Stack(),
                      var redoStack: mutable.Stack[Command] = mutable.Stack(),
                      var message: String = "Welcome to SevenSteps"
                     ) extends Observable {
  var gameState: GameState = Prepare(this)


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
  def addPlayer(name: String): Try[_] = doIt(AddPlayer(name, this))
  def doIt(com: Command): Try[_] = {
    val explored = gameState.exploreCommand(com)
    explored match {
      case Success(_) =>
        undoStack.push(com)
        redoStack.clear()
      case Failure(e) => message = e.getMessage
    }
    notifyObservers()
    explored
  }
  def newGrid(colors: String, cols: Int): Try[_] = doIt(NewGrid(colors, cols, this))
  def startGame(): Try[_] = doIt(StartGame(this))
  def nextPlayer(): Try[_] = doIt(NextPlayer(this))
  def setStone(row: Int, col: Int): Try[_] = doIt(SetStone(row, col, this))
  def undo(): Try[_] = {
    if (undoStack.nonEmpty) {
      val temp = undoStack.pop()
      val temp2 = temp.undo()
      temp2 match {
        case Success(_) =>
          message = "Undo: " + message
          redoStack.push(temp)
        case Failure(e) => message = e.getMessage
      }
      notifyObservers()
      temp2
    } else {
      message = "Can't undo now!"
      Failure(new Exception(message))
    }
  }
  def redo(): Try[_] = {
    if (redoStack.nonEmpty) {
      val temp = redoStack.pop()
      val temp2 = temp.doIt()
      temp2 match {
        case Success(s) =>
          message = "Redo: " + s
          undoStack.push(temp)
        case Failure(e) => message = e.getMessage
      }
      notifyObservers()
      temp2
    } else {
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
  def doIt(): Try[_]
  def undo(): Try[_]
}

case class AddPlayer(name: String, c: Controller) extends Command {
  val player = Player(name)
  override def doIt(): Try[_] = {
    c.players = c.players.push(player)
    c.message = "Added Player " + name
    Success()
  }
  override def undo(): Try[_] = {
    c.players = c.players.pop()
    c.message = "Deleted Player"
    Success()
  }
}

case class NewGrid(colors: String, cols: Int, c: Controller) extends Command {
  var oldGrid: Grid = c.grid
  override def doIt(): Try[_] = {
    oldGrid = c.grid
    c.grid = new Grid(colors, cols)
    c.message = "Build new Grid"
    Success()
  }
  override def undo(): Try[_] = {
    c.grid = oldGrid
    c.message = "Deleted new Grid"
    Success()
  }
}

case class StartGame(c: Controller) extends Command {
  override def doIt(): Try[_] = {
    if (c.players.nonEmpty && c.grid.nonEmpty) {
      c.gameState = Play(c)
      val colors = c.grid.getColors
      c.players = c.players.setColors(colors)
      c.bag = c.bag.copy(colors = colors)
      c.bag.fillup()
      c.prepareNewPlayer()
      c.message = "Started the game"
      Success()
    } else {
      Failure(new Exception("Can't start the game: Not enough Players"))
    }
  }
  override def undo(): Try[_] = {
    c.undoStack.clear()
    Failure(new Exception("You can't undo the start of the game!"))
  }
}

case class NextPlayer(c: Controller) extends Command {
  override def doIt(): Try[_] = {
    c.players = c.players.next()
    c.prepareNewPlayer()
    c.message = "Player " + c.getCurPlayer.name + " it is your turn!"
    Success()
  }
  override def undo(): Try[_] = {
    c.undoStack.clear()
    Failure(new Exception("You can't undo to previous player!"))
  }
}

case class SetStone(row: Int, col: Int, c: Controller) extends Command {
  override def doIt(): Try[_] = {
    c.grid.cell(row, col) match {
      case Failure(_) => Failure(new Exception("You have to set a Stone on height " + (c.curHeight - 1) + " or " + c.curHeight))
      case Success(cell) => if (c.lastCells.nonEmpty) {
        if (!isNeighbour(row, col, c)) {
          return Failure(new Exception("You have to set a Stone neighboring to the last Stone!"))
        }
      }
        if (c.lastCells.contains((row, col))) {
          return Failure(new Exception("You can't set on a cell twice!"))
        }
        val dif = c.curHeight - cell.height
        if (dif == 0 | dif == 1) {
          c.getCurPlayer.placeStone(cell.color, cell.height) match {
            case Failure(e) => Failure(e)
            case Success(player) => c.grid = c.grid.set(row, col, cell.height + 1)
              c.players = c.players.updateCurPlayer(player)
              c.curHeight = cell.height + 1
              c.lastCells.push((row, col))
              c.message = "You set a stone"
              Success()
          }
        } else {
          Failure(new Exception("You have to set a Stone on height " + (c.curHeight - 1) + " or " + c.curHeight))
        }
    }
  }
  def isNeighbour(row: Int, col: Int, c: Controller): Boolean = (math.abs(row - c.lastCells.head._1) + math.abs(col - c.lastCells.head._2)) == 1
  override def undo(): Try[_] = {
    val cell = c.grid.cell(row, col).get
    c.grid = c.grid.set(row, col, cell.height - 1)
    c.players = c.players.updateCurPlayer(c.players.getCurPlayer.incPoints(-cell.height).incColor(cell.color, +1))
    c.curHeight = cell.height - 1
    c.lastCells.pop()
    c.message = "Take the Stone"
    Success()
  }
}

// ##################### Finite State Machine #######################
trait GameState {
  def exploreCommand(com: Command): Try[_]
}

case class Prepare(c: Controller) extends GameState {
  override def exploreCommand(com: Command): Try[_] = {
    com match {
      case command: AddPlayer => command.doIt()
      case command: NewGrid => command.doIt()
      case command: StartGame => command.doIt()
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}

case class Play(c: Controller) extends GameState {
  override def exploreCommand(com: Command): Try[_] = {
    com match {
      case command: NextPlayer => command.doIt()
      case command: SetStone => command.doIt()
      case _ => Failure(new Exception("ILLEGAL COMMAND"))
    }
  }
}

