import com.google.inject.{AbstractModule, Guice, Inject}
import com.google.inject.assistedinject.{Assisted, FactoryModuleBuilder}
import de.htwg.se.SevenSteps.Di._



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

val injetor = Guice.createInjector(new SudokuModule)
println(injetor.getInstance(classOf[PaymentFactory]).create("Plablub"))