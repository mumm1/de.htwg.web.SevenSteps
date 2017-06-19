package de.htwg.se.SevenSteps.util

import scala.collection.mutable
import scala.util.{Failure, Try}

/**
  * Created by tobias on 11.05.17.
  */
case class UndoManager(
                        var undoStack: mutable.Stack[Command] = mutable.Stack(),
                        var redoStack: mutable.Stack[Command] = mutable.Stack()
                      ) {
  def clearUndoStack(): Unit = undoStack.clear()
  def doIt(com: Command): Try[_] = {
    val result = com.doIt()
    if (result.isSuccess) {
      undoStack.push(com)
      redoStack.clear()
    }
    result
  }
  def undo(): Try[_] = {
    if (undoStack.nonEmpty) {
      val temp = undoStack.pop()
      val result = temp.undo()
      if (result.isSuccess) {
        redoStack.push(temp)
      }
      result
    } else {
      Failure(new Exception("Can't undo now!"))
    }
  }
  def redo(): Try[_] = {
    if (redoStack.nonEmpty) {
      val temp = redoStack.pop()
      val result = temp.doIt()
      if (result.isSuccess) {
        undoStack.push(temp)
      }
      result
    } else {
      Failure(new Exception("Can't redo now!"))
    }
  }

}
