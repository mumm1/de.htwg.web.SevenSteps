package de.htwg.se.SevenSteps.Di

import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.{AbstractModule, Guice}

trait PaymentFactory {
  def create(startDate: String): IPayment
}

class SudokuModule extends AbstractModule {

  def configure() = {
    install(new FactoryModuleBuilder()
      .implement(classOf[IPayment],classOf[RealPayment])
      .build(classOf[PaymentFactory]))
    bind(classOf[ICretitServide]).to(classOf[RealCredit])
  }

}

case class InjectorTag()

/**
  * Created by tobias on 05.06.17.
  */
object main {

  def main(args: Array[String]): Unit = {

    val injetor = Guice.createInjector(new SudokuModule)
    println(injetor.getInstance(classOf[PaymentFactory]).create("Plablub"))
    val imeth = injetor.getInstance(classOf[InjectorTag])
  }
}
