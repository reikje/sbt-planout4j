import Keys._

lazy val commonSettings = Seq(
  version in ThisBuild := "0.0.2",
  organization in ThisBuild := "me.rschatz",
  libraryDependencies in ThisBuild ++= Seq(
    "com.glassdoor.planout4j" % "planout4j-core" % "1.2",
    "com.glassdoor.planout4j" % "planout4j-compiler" % "1.2",
    "ch.qos.logback" % "logback-classic" % "1.2.3"
  )
)

lazy val root = (project in file(".")).
  settings(commonSettings).
  settings(
    sbtPlugin := true,
    crossSbtVersions := Vector("0.13.18", "1.2.8"),
    name := "sbt-planout4j",
    description := "Compiles Planout4j yaml files to Planout language files",
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    publishMavenStyle := false,
    bintrayRepository := "sbt-plugins",
    bintrayOrganization in bintray := None
  )
