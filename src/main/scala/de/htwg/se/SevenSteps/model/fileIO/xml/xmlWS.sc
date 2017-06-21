object TypeMagic {
  // import scala.tools.scalap.scalax.rules.scalasig.MethodSymbol
  import scala.reflect.runtime.universe._

  type productable[T] = Class[T with Product]
  val getFieldGetter = new Memoize1(makeFieldGetter)
  implicit val mirror = runtimeMirror(getClass.getClassLoader)
  val getFieldsOf = getFields(_)
  def makeFieldGetter[T <: Product](clazz: productable[_]) = makeFieldGetterF(getFieldsL(clazz))
  def getFieldsL(clazz: productable[_]) = this.synchronized {
    {
      val args = getType(clazz).member(nme.CONSTRUCTOR).asMethod.paramss.head
      val argNames = for (a <- args) yield a.name.decoded
      for {
        field <- clazz.getDeclaredFields
        name = field.getName
        if argNames contains name
      } yield (name, field)
    }.toMap
  }
  def getType[T](clazz: Class[T])(implicit runtimeMirror: Mirror) =
    runtimeMirror.classSymbol(clazz).toType
  def makeFieldGetterF(fields: Map[String, java.lang.reflect.Field]): (Product => Map[String, Any]) =
    (input: Product) => {
      for {
        (name, field) <- fields.toSeq
        madeAccessible = field.setAccessible(true)
        value = field.get(input)
      }
        yield (name, value)
    }.toMap
  def getFields(a: Product) = getFieldGetter(a.getClass.asInstanceOf[productable[_]])(a)

  class Memoize1[-T, +R](f: T => R) extends (T => R) {
    private[this] val vals = scala.collection.mutable.Map.empty[T, R]
    def apply(x: T): R = vals.getOrElseUpdate(x, f(x))
  }

}

object XmlPrinting {

  object HeadNode {
    implicit def headNode(nodes: Traversable[scala.xml.Node]): scala.xml.Node = nodes.head
  }

  import TypeMagic.getFieldsOf

  implicit class pretty[A](a: A) {
    def xmlString: Traversable[scala.xml.Node] = a match {
      case list: Traversable[_] =>
        <list>
          {list.collect {
          case subList: Traversable[_] => subList.xmlString
          case item => {
            <item>
              {item.childXmlString}
            </item>
          }
        }}
        </list>
      case x: Any => x.childXmlString
    }
    def childXmlString: Traversable[scala.xml.Node] = a match {
      case list: Traversable[_] =>
        for (item <- list; sub <- item.childXmlString)
          yield sub
      case product: Product =>
        <node>
          {for {
          (title, value) <- getFieldsOf(product)
          base = <node/>.copy(label = title)
        } yield value match {
          case Some(x) => base.copy(child = x.childXmlString.toSeq)
          case None => <node xsi:nil="true"/>.copy(label = title)
          case x => base.copy(child = x.childXmlString.toSeq)
        }}
        </node>.copy(label = product.productPrefix)
      case null =>
          <nil xsi:nil="true"/>
      case x => scala.xml.Text(x.toString)
    }
  }

}

object XmlPrintingTest {

  import XmlPrinting._
  import HeadNode.headNode

  val human = Human("Mr.", "Johnny", List(Coat(Option("BOSS")), Coat(None), Coat(Option("Armani"))))
  val printer = new scala.xml.PrettyPrinter(80, 2)

  case class Coat(brand: Option[String])

  case class Human(title: String, name: String, coats: Seq[Coat])

  println(printer.format(human.xmlString))
  println(printer.format(List(List("a", "b"), List(5, 6), List(human, human)).xmlString))
  println(printer.format(human.xmlString))
  println(printer.format(List(List("a", "b"), List(5, 6), List(human, human)).xmlString))
}