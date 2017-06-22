import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.bag.basicImpl.Bag
import de.htwg.se.SevenSteps.model.grid.IGrid
import de.htwg.se.SevenSteps.model.grid.basicImpl.{Cell, Grid}
import de.htwg.se.SevenSteps.model.player.IPlayers

import scala.collection.immutable.Map

object XmlPrintingTest {

  import de.htwg.se.SevenSteps.model.player.basicImpl._
  import de.htwg.se.SevenSteps.model.bag.basicImpl
  import com.google.inject.Guice
  import de.htwg.se.SevenSteps.aview.gui.SwingGui
  import de.htwg.se.SevenSteps.aview.tui._
  import de.htwg.se.SevenSteps.controller.IController
  import scala.io.StdIn.readLine

  val injector = Guice.createInjector(new SevenStepsModule)
  val pl = Player("Julius", 50, None)
  c.state.players = c.state.players.push("OLA")
  c.state.players = c.state.players.push("Julius")
  c.state.message = "Added Player " + "OLA"
  c.newGrid("aabb", 2)
  c.state.bag = c.state.bag.copy1(List('a', 'b', 'c'))
  val pl2 = pl.setColors(List('a', 'b', 'c'))
  val p1 = new Player("Julius", 50, None)
  val p3 = p1.setColors(List('a', 'b', 'c'))
  val p2 = new Player("Tobias", 50, None)
  val p4 = p2.setColors(List('a', 'b', 'c'))
  val pls2 = pls3.push(p3)
  val state = ControllerState
  1 + 2
  var c = injector.getInstance(classOf[IController])
  //PlayerToXML(pl2)
  var pls3 = new Players()
  var pls = pls2.push(p4)
  var bag = Bag(Vector("g", "dr"), Vector("a", "b"))
  def sooo(c: ControllerState) = {
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
  3 + 4
  PlayersToXML(pls)
  4 + 4
  def bagColorsToXML(bag: IBag) = {
    bag.colors.map { entry =>
      val colors = entry
      <bag col={colors}></bag>
    }
  }
  1 + 1
  sooo(c.state)
}