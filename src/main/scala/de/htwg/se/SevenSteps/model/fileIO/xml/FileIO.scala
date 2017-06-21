package de.htwg.se.SevenSteps.model.fileIoComponent.fileIoXmlImpl

/**
  * Created by acer1 on 16.06.2017.
  */

import de.htwg.se.SevenSteps.model.fileIO.IFileIO
import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.player.IPlayer
import scala.xml.{NodeSeq, PrettyPrinter}

class FileIO extends IFileIO {
  def allToXML(con: IController) = {
    <controller lastCells={con.lastCells} curHeight={con.curHeight} message={con.message}>
    </controller>
  }
  def playerToXML(player: IPlayer): Unit = {
    <player points={player.points} name={player.name}
      }


}
