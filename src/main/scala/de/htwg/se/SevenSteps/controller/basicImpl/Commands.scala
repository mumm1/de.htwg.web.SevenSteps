package de.htwg.se.SevenSteps.controller.basicImpl

import de.htwg.se.SevenSteps.model.grid.{ICell, IGrid}
import de.htwg.se.SevenSteps.util.Command

import scala.util.{Failure, Success, Try}

case class AddPlayer(name: String, c: Controller) extends Command {
  override def doIt(): Try[_] = {
    c.state.players = c.state.players.push(name)
    c.state.message = "Added Player " + name
    Success()
  }
  override def undo(): Try[_] = {
    c.state.players = c.state.players.pop()
    c.state.message = "Deleted Player " + name
    Success()
  }
}

case class SetColor(row: Int, col: Int,color:Char, c: Controller) extends Command {
  var oldColor = ' '
  override def doIt(): Try[_] = {
    c.state.grid.cell(row, col) match {
      case Failure(_) => Failure(new Exception("You have to set a stone inside the grid"))
      case Success(cell) =>
        oldColor = cell.color
        c.state.grid = c.state.grid.set(row, col, color)
        c.state.message = "Grid was colored"
        Success()
    }

  }
  override def undo(): Try[_] = {
    c.state.grid = c.state.grid.set(row, col, oldColor)
    c.state.message = "Grid was colored back"
    Success()
  }
}

case class NewGrid(colors: String, cols: Int, c: Controller) extends Command {
  var oldGrid: IGrid = c.state.grid
  override def doIt(): Try[_] = {
    oldGrid = c.state.grid
    c.state.grid = c.gridFactory.newGrid(colors, cols)
    c.state.message = "Build new Grid"
    Success()
  }
  override def undo(): Try[_] = {
    c.state.grid = oldGrid
    c.state.message = "Deleted new Grid"
    Success()
  }
}

case class StartGame(c: Controller) extends Command {
  override def doIt(): Try[_] = {
    if (c.state.players.nonEmpty && c.state.grid.nonEmpty) {
      c.state.gameState = Play()
      val colors = c.state.grid.getColors
      c.state.players = c.state.players.setColors(colors)
      c.state.bag = c.state.bag.copy1(colors)
      c.state.bag.fillup()
      c.prepareNewPlayer()
      c.state.message = "Started the game"
      Success()
    } else {
      Failure(new Exception("Can't start the game: Not enough Players"))
    }
  }
  override def undo(): Try[_] = {
    c.undoManager.clearUndoStack()
    Failure(new Exception("You can't undo the start of the game!"))
  }
}

case class NextPlayer(c: Controller) extends Command {
  override def doIt(): Try[_] = {
    if (c.isGameEnd || c.isDeadlock) {
      c.finish()
    } else {
      c.state.players = c.state.players.next()
      c.prepareNewPlayer()
      c.state.message = "Player " + c.state.players.getCurPlayer.name + " it is your turn!"
    }
    Success()
  }
  override def undo(): Try[_] = {
    c.undoManager.clearUndoStack()
    Failure(new Exception("You can't undo now!"))
  }
}

case class SetStone(row: Int, col: Int, c: Controller) extends Command {

  override def doIt(): Try[_] = {
    isCellLegal match {
      case Failure(e) => Failure(e)
      case Success(cell) =>
        val dif = c.state.curHeight - cell.height
        if (dif == 0 | dif == 1) {
          c.state.players.getCurPlayer.placeStone(cell.color, cell.height) match {
            case Failure(e) => Failure(e)
            case Success(player) => c.state.grid = c.state.grid.set(row, col, cell.height + 1)
              c.state.players = c.state.players.updateCurPlayer(player)
              c.state.curHeight = cell.height + 1
              c.state.lastCells :+= (row, col)
              c.state.message = "You set a stone"
              Success()
          }
        } else {
          Failure(new Exception("You have to set a Stone on height " + (c.state.curHeight - 1) + " or " + c.state.curHeight))
        }
    }
  }
  def isCellLegal: Try[ICell] = {
    c.state.grid.cell(row, col) match {
      case Failure(_) => Failure(new Exception("You have to set a stone inside the grid"))
      case Success(cell) =>
        if (c.state.lastCells.nonEmpty && !isNeighbour(row, col, c)) {
          Failure(new Exception("You have to set a Stone neighboring to the last Stone!"))
        }
        else {
          if (c.state.lastCells.contains((row, col))) {
            Failure(new Exception("You can't set on a cell twice!"))
          }
          else {
            Success(cell)
          }
        }
    }
  }
  def isNeighbour(row: Int, col: Int, c: Controller): Boolean = (math.abs(row - c.state.lastCells.last._1) + math.abs(col - c.state.lastCells.last._2)) == 1
  override def undo(): Try[_] = {
    val cell = c.state.grid.cell(row, col).get
    c.state.grid = c.state.grid.set(row, col, cell.height - 1)
    c.state.players = c.state.players.updateCurPlayer(c.state.players.getCurPlayer.incPoints(-cell.height).incColor(cell.color, +1))
    c.state.curHeight = cell.height - 1
    c.state.lastCells = c.state.lastCells.init
    c.state.message = "You take the Stone back"
    Success()
  }
}

case class NewGame(c: Controller) extends Command {
  override def doIt(): Try[_] = {
    c.state.gameState = Prepare()
    c.state.grid = c.state.grid.resetHeights
    c.state.players = c.state.players.reset
    c.state.bag = c.state.bag.reset
    c.state.message = "Started new Game"
    Success()
  }
  override def undo(): Try[_] = {
    c.undoManager.clearUndoStack()
    Failure(new Exception("You can't undo the start of a new game!"))
  }
}