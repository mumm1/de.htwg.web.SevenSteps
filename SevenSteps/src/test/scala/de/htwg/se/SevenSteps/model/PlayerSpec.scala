package de.htwg.se.SevenSteps.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec {

	"A Player" should {

		val p = new Player("Julius",50)

				"have a name" in {
			Player("Julius", 50).name should be("Julius")
		}
		"have points" in {
			Player ("Julius", 50).points should be (50)
		}

		"have a char in map" in {
			p.map.contains('g') should be (true)
		}

		"toString look like" in {
			p.toString should be ("Julius: g=0, b=0, a=0, c=0, Points=50")
			}

		
	}






}