package de.htwg.se.SevenSteps.Di

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted

/**
  * Created by tobias on 05.06.17.
  */
case class RealPayment @Inject() ( creditService: ICretitServide,
                                   @Assisted() amount : String
                                 ) extends IPayment {
  override def toString: String = amount
}
