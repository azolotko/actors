name := "actors"

organization := "me.zolotko"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.8"

scalacOptions ++= Seq("-feature",
                      "-unchecked",
                      "-deprecation",
                      "-Ywarn-value-discard")

licenses := Seq(
  "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))
