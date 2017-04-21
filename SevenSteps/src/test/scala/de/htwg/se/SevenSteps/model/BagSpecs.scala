package de.htwg.se.SevenSteps.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class BagSpec extends WordSpec {
  
  	"A Bag" should {
		val bag = new Player("Sack")
		"have a name" in {
			bag.name should be("Sack")
		}
  	} 
  
  	
  
  
  
  
  
  
}