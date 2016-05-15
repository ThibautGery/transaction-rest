name := """transaction-rest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaWs
)

libraryDependencies += "org.assertj" % "assertj-core" % "3.4.1"