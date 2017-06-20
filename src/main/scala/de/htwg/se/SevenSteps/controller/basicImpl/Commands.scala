package de.htwg.se.SevenSteps.controller.basicImpl

import de.htwg.se.SevenSteps.model.grid.{ICell, IGrid}
import de.htwg.se.SevenSteps.util.Command

import scala.util.{Failure, Success, Try}

case class AddPlayer(name: String, c: Controller) extends Command {
  override def doIt(): Try[_] = {
    c.players = c.players.push(name)
    c.message = "Added Player " + name
    Success()
  }
  override def undo(): Try[_] = {
    c.players = c.players.pop()
    c.message = "Deleted Player " + name
    Success()
  }
}

case class SetColor(row: Int, col: Int,color:Char, c: Controller) extends Command {
  var oldColor = ' '
  override def doIt(): Try[_] = {
    c.grid.cell(row, col) match {
      case Failure(_) => Failure(new Exception("You have to set a stone inside the grid"))
      case Success(cell) =>
        oldColor = cell.color
        c.grid = c.grid.set(row, col,color)
        c.message = "Grid was colored"
        Success()
    }

  }
  override def undo(): Try[_] = {
    c.grid = c.grid.set(row, col,oldColor)
    c.message = "Grid was colored back"
    Success()
  }
}

case class NewGrid(colors: String, cols: Int, c: Controller) extends Command {
  var oldGrid: IGrid = c.grid
  override def doIt(): Try[_] = {
    oldGrid = c.grid
    c.grid = c.gridFactory.newGrid(colors,cols)
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
      c.gameState = Play()
      val colors = c.grid.getColors
      c.players = c.players.setColors(colors)
      c.bag = c.bag.copy1(colors)
      c.bag.fillup()
      c.prepareNewPlayer()
      c.message = "Started the game"
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
      c.players = c.players.next()
      c.prepareNewPlayer()
      c.message = "Player " + c.players.getCurPlayer.name + " it is your turn!"
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
        val dif = c.curHeight - cell.height
        if (dif == 0 | dif == 1) {
          c.players.getCurPlayer.placeStone(cell.color, cell.height) match {
            case Failure(e) => Failure(e)
            case Success(player) => c.grid = c.grid.set(row, col, cell.height + 1)
              c.players = c.players.updateCurPlayer(player)
              c.curHeight = cell.height + 1
              c.lastCells :+= (row, col)
              c.message = "You set a stone"
              Success()
          }
        } else {
          Failure(new Exception("You have to set a Stone on height " + (c.curHeight - 1) + " or " + c.curHeight))
        }
    }
  }
  def isCellLegal: Try[ICell] = {
    c.grid.cell(row, col) match {
      case Failure(_) => Failure(new Exception("You have to set a stone inside the grid"))
      case Success(cell) =>
        if (c.lastCells.nonEmpty && !isNeighbour(row, col, c)) {
          Failure(new Exception("You have to set a Stone neighboring to the last Stone!"))
        }
        else {
          if (c.lastCells.contains((row, col))) {
            Failure(new Exception("You can't set on a cell twice!"))
          }
          else {
            Success(cell)
          }
        }
    }
  }
  def isNeighbour(row: Int, col: Int, c: Controller): Boolean = (math.abs(row - c.lastCells.last._1) + math.abs(col - c.lastCells.last._2)) == 1
  override def undo(): Try[_] = {
    val cell = c.grid.cell(row, col).get
    c.grid = c.grid.set(row, col, cell.height - 1)
    c.players = c.players.updateCurPlayer(c.players.getCurPlayer.incPoints(-cell.height).incColor(cell.color, +1))
    c.curHeight = cell.height - 1
    c.lastCells = c.lastCells.init
    c.message = "You take the Stone back"
    Success()
  }
}

case class NewGame(c: Controller) extends Command {
  override def doIt(): Try[_] = {
    c.gameState = Prepare()
    c.grid = c.grid.resetHeights
    c.players = c.players.reset
    c.bag = c.bag.reset
    c.message = "Started new Game"
    Success()
  }
  override def undo(): Try[_] = {
    c.undoManager.clearUndoStack()
    Failure(new Exception("You can't undo the start of a new game!"))
  }
}