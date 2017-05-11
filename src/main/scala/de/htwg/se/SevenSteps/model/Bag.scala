package de.htwg.se.SevenSteps.model

/**
  * Created by acer1 on 04.05.2017.
  */
case class Bag(var bag: Array[Char] = new Array[Char](0),
                var entfernt : Int = 0,
                var aktuell: Int = 0,
                var random : Boolean,
                colors: List[Char] = List[Char]() ) {

  def fillup(): Unit = {
    for ((c) <- colors) {
      for (i <- 0 to 6)
        insert(c)
    }
  }


  def insert(x : Char): Unit ={
    var bag2 = new Array[Char](bag.length + 1 )
    var i = 0
    while (i < bag.length) {
      bag2(i) = bag(i)
      i += 1
    }
    bag2(bag2.length - 1) = x
    bag = bag2
  }

  def get(): Char = { //Zieht ein Objekt aus dem Pool

    var rand  = 0.35
    if (random)
      rand = Math.random()

    var tmp = 'a'
    var Ausgabe  = 'a'
    if (bag.length >= (entfernt + 1)) {
      aktuell = bag.length - entfernt
      // val gezogen = (Math.random * aktuell).asInstanceOf[Int]
      val gezogen = (rand * aktuell).asInstanceOf[Int]
      Ausgabe = bag(gezogen)
      tmp = bag(gezogen) //Gezogenen Wert mit dem Wert am Ende des Feldes
      bag(gezogen) = bag(bag.length - (entfernt + 1)) //vertauschen
      bag(bag.length - entfernt - 1) = tmp
      entfernt += 1
      Ausgabe //gezogenen Wert Ausgeben
    }
    else '$'
  }



  def get(m: Int): Array[Char] = { //Zieht eine vorgegebene Anzahl von Objekten aus dem Pool
    var bag3 = new Array[Char](m)
    var i = 0
    while (i < m){
      bag3(i) = get()
      i += 1
    }
    bag3 //Rückgabe des Arrays mit den gezogenen Werten
  }
}
