package de.htwg.se.SevenSteps.model.fileIO.xml

import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState
import de.htwg.se.SevenSteps.model.fileIO.IFileIO
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.bag.basicImpl.Bag
import de.htwg.se.SevenSteps.model.grid.IGrid
import de.htwg.se.SevenSteps.model.grid.basicImpl.{Cell, Grid}
import de.htwg.se.SevenSteps.model.player.IPlayers
import scala.collection.immutable.Map
import de.htwg.se.SevenSteps.model.player.basicImpl._
import de.htwg.se.SevenSteps.model.bag.basicImpl
import com.google.inject.Guice
import de.htwg.se.SevenSteps.aview.gui.SwingGui
import de.htwg.se.SevenSteps.aview.tui._
import de.htwg.se.SevenSteps.controller.IController
import scala.io.StdIn.readLine

case class FileIO() extends IFileIO {
  var help: ControllerState = null
  // def save(cState: ControllerState): Unit = help = cState
  def load: ControllerState = help
  def save(c: ControllerState) = {
    <game players={PlayersToXML(c.players)} bag={bagToXML(c.bag)} grid={gridToXML(c.grid)} rest={rest(c)}></game>
  }
  def PlayersToXML(players: IPlayers) = {
    players.players.map { entry =>
      val pl = entry
      <pls pls2={PlayerToXML(pl)}></pls>
    }
  }
  def PlayerToXML(player: Player) = {
    <pl points={player.points.toString} name={player.name}>
      map=
      {stoneMapToXML(player.map.get)}
    </pl>
  }
  def stoneMapToXML(stones: Map[String, Double]) = {
    stones.map { entry =>
      val (key, value) = entry
      <steine farbe={key.toString}>
        {value.toString}
      </steine>
    }
  }
  def gridToXML(grid: IGrid) = {
    <grid rows={grid.rows.toString} col={grid.cols.toString}>
      {grid.grid.map { entry =>
      val cell = entry
      <cellen celll={cellToXML(cell)}></cellen>
    }}
    </grid>
  }
  def cellToXML(cell: Cell) = {
    <cell colorCell={cell.color.toString} height={cell.height.toString}></cell>
  }
  def rest(state: ControllerState) = {
    <rest mes={state.message} cur={state.curHeight.toString} last={lastCellsToXML(state.lastCells)}>
    </rest>
  }
  def lastCellsToXML(stones: Vector[(Int, Int)]) = {
    stones.map { entry =>
      val (row, col) = entry
      <cell row={row.toString} col={col.toString}>
      </cell>
    }
  }
  def bagToXML(bag: IBag) = {
    <alles a={bagBagToXML(bag)} b={bagColorsToXML(bag)}></alles>
  }
  def bagBagToXML(bag: IBag) = {
    bag.bag.map { entry =>
      val bag = entry
      <blub bag2={bag}></blub>
    }
  }
  def bagColorsToXML(bag: IBag) = {
    bag.colors.map { entry =>
      val colors = entry
      <bag col={colors}></bag>
    }
  }
}


