import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.basicImpl.ControllerState
import de.htwg.se.SevenSteps.model.fileIO.json.CustomGenson.customGenson._

val injector = Guice.createInjector(new SevenStepsModule)
val c = injector.getInstance(classOf[ControllerState])
val json = toJson(c)
val s = fromJson[ControllerState](json)