import com.google.inject.Guice
import de.htwg.se.SevenSteps.SevenStepsModule
import de.htwg.se.SevenSteps.controller.IController
import de.htwg.se.SevenSteps.model.bag.IBag
import de.htwg.se.SevenSteps.model.bag.basicImpl.Bag
import de.htwg.se.SevenSteps.model.fileIO.json.CustomGenson.customGenson._
import play.api.libs.json._

val injector = Guice.createInjector(new SevenStepsModule)
val c = injector.getInstance(classOf[IController])
val S1 = toJson(new Bag())
fromJson[IBag](S1)
val jsonString = Json.obj("bag" -> toJson(c.bag),
  "grid" -> toJson(c.grid)).toString()
val json34 = Json.obj("bag" -> toJson(c.bag))
val json2: JsValue = Json.parse(jsonString)
val S2 = (json2 \ "bag").get.toString().replace("\\", "")
val S3 = S2.substring(1, S2.length - 1)
S3 == unpackJson((json2 \ "bag").get)
//fromJson[IBag]((json2 \ "bag").get.toString())
def unpackJson(json: JsValue): String = {
  val help = json.toString().replace("\\", "")
  help.substring(1, help.length - 1)
}