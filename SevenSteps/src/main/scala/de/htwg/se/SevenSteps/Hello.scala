package de.htwg.se.SevenSteps

import de.htwg.se.SevenSteps.model.Student

object Hello {
  def main(args: Array[String]): Unit = {
    val student = Student("Your Name")
    println("Hello, " + student.name)
  }
}
