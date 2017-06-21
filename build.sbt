name          := "htwg-scala-seed"
organization  := "de.htwg.se"
version       := "0.0.1"
scalaVersion := "2.11.0"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= {
  val scalaTestV       = "3.0.0-M15"
  val scalaMockV       = "3.2.2"
  Seq(
    "org.scalatest" %% "scalatest"                   % scalaTestV       % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV       % "test"
  )
}

libraryDependencies += "junit" % "junit" % "4.8" % "test"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11+"

libraryDependencies += "com.google.inject.extensions" % "guice-assistedinject" % "4.0"

libraryDependencies += "com.google.inject" % "guice" % "4.0"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.5.15"

libraryDependencies += "com.google.code.gson" % "gson" % "2.8.1"

libraryDependencies += "com.owlike" % "genson-scala_2.11" % "1.4"

libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.7"


