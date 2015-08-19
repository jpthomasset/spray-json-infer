name := "spray-json-infer"

version := "1.0"

scalaVersion := "2.11.6"

// Spray
libraryDependencies += "io.spray" %% "spray-json" % "1.3.2"

// Test lib
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

scalacOptions += "-target:jvm-1.8"
