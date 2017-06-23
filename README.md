[![Travis](https://travis-ci.org/t123obi/de.htwg.se.SevenSteps.svg?branch=master)]()


[![codecov](https://codecov.io/gh/t123obi/de.htwg.se.SevenSteps/branch/master/graph/badge.svg)](https://codecov.io/gh/t123obi/de.htwg.se.SevenSteps) (without View-Layer, Mocks & main)


SevenSteps (Game)
=========================

This is a scala project as used in the
class Software Engineering at the University of Applied Science HTWG Konstanz

### Goels of this Project

* MVC Architecture
* git and Scrum
* Design Pattern
* Test Driven Development
* clean code
* Dependency Injection
* Build Tool & Continuous Integration (sbt & Travis CI)
* Serialization (json, xml)
* Scala (FP and OOP)


### Used Design Pattern

* Observer Pattern (for MVC inversion of control)
* Command Pattern (for undo-redo Stack)
* State Pattern (for game phases)
* Factory Pattern (alternative to Dependency Injection)

### Other cool stuff

* Scala Mixins for [sharing tests][1] between different implementations of the FileIO Component
* Automated json mapping with the [Genson][2]-Framework
* Code Coverage visible in github through [Codecov][3]

### Game Overview

##### Prepare the Game

* Add Players
* Set a grid size you want to play on
* Color the grid like you want

##### Play the Game

* Every Player draws up to 7 Stones at the beginning of his turn.
* You can set on the colored grid a stone if the color matches.
* The first Stone must be set on height 0 on the grid.
* The next Stone must be set neighboring the last stone and on the same height (or +1) 
* You can't set on the same grid cell twice in the same turn.
* You get Points equal to the height you set the stone, so building higher is better.

##### End of Game

* The Game ends when all players can't set on height 0 anymore.
* The Player with the most points wins the game.

### TUI & GUI

[![Foo](https://picload.org/image/rpgigril/screenshotfrom2017-06-2311-50-.png)](https://picload.org/)

[1]: http://www.scalatest.org/user_guide/sharing_tests
[2]: https://owlike.github.io/genson/
[3]: https://codecov.io/

