package de.htwg.se.SevenSteps.model.fileIO.json

import com.owlike.genson._

object CustomGenson {
  val customGenson = new ScalaGenson(
    new GensonBuilder()
      .useIndentation(true) // use pretti-print
      .useClassMetadata(true) // save full class name (traits)
      .useRuntimeType(true) //
      .withBundle(ScalaBundle().useOnlyConstructorFields(false)) // also internal vals & vars
      .create()
  )
}

trait Person

case class Teacher(name: String) extends Person

case class Student(name: String) extends Person

case class School(persons: Vector[Person]) {
  val num: Int = persons.length
}

object gensonExample {
  def main(args: Array[String]): Unit = {
    import CustomGenson.customGenson._
    val school = School(Vector(Teacher("Hans"), Student("Peter")))
    val jsonSchool = toJson(school)
    println(jsonSchool)
    val schoolFromJson = fromJson[School](jsonSchool)
    println(schoolFromJson)
    println(school == schoolFromJson)
  }
}
