import Keys._

sbtPlugin := true

organization := "me.rschatz"

name := "sbt-planout4j"

version := "0.0.1"

scalaVersion := "2.10.5"

lazy val planout4jVersion = settingKey[String]("Version of the Planout4j library")

planout4jVersion := "1.1"

libraryDependencies ++= Seq(
  "com.glassdoor.planout4j" % "planout4j-core" % planout4jVersion.value,
  "com.glassdoor.planout4j" % "planout4j-compiler" % planout4jVersion.value,
  "ch.qos.logback" % "logback-classic" % "1.1.3"
)