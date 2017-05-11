package de.htwg.se.SevenSteps.util

import scala.util.Try

trait Command {
  def doIt(): Try[_]
  def undo(): Try[_]
}