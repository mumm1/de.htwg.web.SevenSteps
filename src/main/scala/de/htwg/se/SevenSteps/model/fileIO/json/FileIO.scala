package de.htwg.se.SevenSteps.model.fileIO.json

import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.fileIO.json.CustomGenson.customGenson._
import de.htwg.se.SevenSteps.model.grid.IGrid
import de.htwg.se.SevenSteps.model.player.IPlayers
import play.api.libs.json.Json
import scala.collection.mutable

case class FileIO() {
  var json: String = ""
  //  def load: Try[(IBag,IGrid,IPlayers,mutable.Stack[(Int,Int)],Int)] = {
  //  }
  def save(bag: IBag, grid: IGrid, players: IPlayers, lastCells: mutable.Stack[(Int, Int)], curHeight: Int): Unit = {
    val jsonObj = Json.obj(
      "bag" -> toJson(bag),
      "grid" -> toJson(grid),
      "players" -> toJson(players),
      "lastCells" -> toJson(lastCells),
      "curHeight" -> toJson(curHeight)
    )
    json = jsonObj.toString()
  }
}
