package de.htwg.se.SevenSteps.controller

import scala.collection.immutable.Map

trait Translatable {
  def translate(trans: Translator): String
}

abstract class Translator {
  def translate(test: String): String
}

object dictEnDe extends Translator {
  private val dict = Map().updated("addPlayer", "Hinzugefügter Spieler: ").updated("welcome", "Willkommen zu 7Steps")
  override def translate(text: String): String = dict(text)
}

object dictEnEn extends Translator {
  private val dict = Map().updated("addPlayer", "Added Player: ").updated("welcome", "welcome to 7Steps")
  override def translate(text: String): String = dict(text)
}

object WelcomeMessage extends Translatable {
  override def translate(trans: Translator): String = trans.translate("welcome")
}