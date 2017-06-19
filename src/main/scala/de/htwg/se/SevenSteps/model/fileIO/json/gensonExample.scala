package de.htwg.se.SevenSteps.model.fileIO.json

import com.google.inject.{Guice, Inject}
import com.owlike.genson._
import com.owlike.genson.annotation.JsonCreator
import de.htwg.se.SevenSteps.controller.basicImpl._
import de.htwg.se.SevenSteps.controller.{GameState, IController}
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.grid.{IGrid, IGridFactory}
import de.htwg.se.SevenSteps.model.player.{IPlayer, IPlayers}
import de.htwg.se.SevenSteps.util.{Command, UndoManager}
import de.htwg.se.SevenSteps.{GridFactory, SevenStepsModule}

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

object CustomGenson {
  val customGenson = new ScalaGenson(
    new GensonBuilder()
      //.useIndentation(true) // use pretti-print
      .useClassMetadata(true) // save full class name (traits)
      .useRuntimeType(true) //
      .withBundle(ScalaBundle().useOnlyConstructorFields(true)) // also internal vals & vars
      .create()
  )
}

trait Person

case class Teacher(name: String) extends Person {
  def this() = this("hans")
}

//case class Student(name: String) extends Person
//case class MyTuple(x:Int,y:Int,z:Int)
//case class School(persons: Vector[Person]) {
//  val num: Int = persons.length
//}
case class Controller2 @JsonCreator()
(var players: IPlayers,
 var bag: IBag,
 var gridFactory: IGridFactory,
 var grid: IGrid,
 var gameState: GameState = null,
 var message: String = "Welcome to SevenSteps",
 var curHeight: Int = 0,
 var undoManager: UndoManager = new UndoManager,
 var lastCellX: mutable.Stack[Int] = mutable.Stack(),
 var lastCellY: mutable.Stack[Int] = mutable.Stack()
) extends IController {
  //  var lastCells: mutable.Stack[(Int, Int)] = mutable.Stack()
  // def this(injetor: Injector) = {
  //    this(injetor.getInstance(classOf[IPlayers]),
  //      injetor.getInstance(classOf[IBag]),
  //      injetor.getInstance(classOf[GridFactory]),
  //      injetor.getInstance(classOf[GridFactory]).newGrid(" ", 1))
  //  }
  @Inject()
  def this(players: IPlayers, bag: IBag, gridFactory: IGridFactory) = {
    this(players, bag, gridFactory, gridFactory.newGrid(" ", 1))
  }
  def prepareNewPlayer(): Unit = {
    for (_ <- players.getCurPlayer.getStoneNumber to 6) {
      bag.get() match {
        case Some(col: Char) => players = players.updateCurPlayer(players.getCurPlayer.incColor(col, 1))
        case None =>
      }
    }
    curHeight = 0
    //lastCells.clear()
  }
  def getCurPlayer: IPlayer = {
    players.getCurPlayer
  }
  def isGameEnd: Boolean = bag.isEmpty && players.haveNoStones
  def finish(): Try[Controller2] = {
    gameState = Finish()
    message = "Winner is " + getWinningPlayer.name
    Success(this)
  }
  def getWinningPlayer: IPlayer = {
    var winner = players(0)
    for (i <- 1 until players.length) {
      if (players(i).points > winner.points) {
        winner = players(i)
      }
    }
    winner
  }
  def addPlayer(name: String): Try[Controller2] = Success(this)
  def newGrid(colors: String, cols: Int): Try[Controller2] = Success(this)
  def startGame(): Try[Controller2] = Success(this)
  def nextPlayer(): Try[Controller2] = Success(this)
  def doIt(command: Command): Try[Controller2] = {
    val result = gameState.exploreCommand(command, this)
    unpackError(result)
    notifyObservers()
    wrapController(result)
  }
  def wrapController(t: Try[_]): Try[Controller2] = {
    t match {
      case Success(_) => Success(this)
      case Failure(e) => Failure(e)
    }
  }
  def unpackError(e: Try[_]): Unit = {
    e match {
      case Failure(e) => message = e.getMessage
      case _ =>
    }
  }
  def setStone(row: Int, col: Int): Try[Controller2] = Success(this)
  def newGame(): Try[Controller2] = Success(this)
  def undo(): Try[Controller2] = {
    val result = undoManager.undo()
    unpackError(result)
    notifyObservers()
    wrapController(result)
  }
  def redo(): Try[Controller2] = {
    val result = undoManager.redo()
    unpackError(result)
    notifyObservers()
    wrapController(result)
  }
  def isDeadlock: Boolean = {
    val possibleColorsGrid = grid.getColorsWithHeight0
    val possibleColorsPlayer = players.getAllPossibleColorsFromAllPlayers
    for (color <- possibleColorsGrid) {
      if (possibleColorsPlayer.contains(color)) {
        return false
      }
    }
    true
  }
  def setColor(row: Int, col: Int, color: Char): Try[Controller2] = Success(this)
  override def toString: String = {
    val text = "\n############  " + message + "  ############\n\n"
    val len = text.length()
    text + players.toString + grid.toString + "#" * (len - 2) + "\n"
  }
}


object gensonExample {
  def main(args: Array[String]): Unit = {
    import CustomGenson.customGenson._
    val injector = Guice.createInjector(new SevenStepsModule)
    val c = injector.getInstance(classOf[IController])
    val c2 = Controller2(c.players, c.bag, GridFactory(), c.grid)
    val json = toJson(c2)
    println(json)
    val t2 = (1, 2)
    println(toJson(t2))
    //val cFromJson = fromJson[IController](json)
    //print(cFromJson)
  }
}
