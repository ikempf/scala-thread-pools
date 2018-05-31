lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "com.ikempf",
      scalaVersion := "2.12.6",
      version := "0.1.0-SNAPSHOT"
    )),
  scalacOptions += "-Ypartial-unification",
  name := "scala-thread-pools",
  libraryDependencies += "io.monix"          %% "monix"      % "3.0.0-RC1",
  libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.12",
  libraryDependencies += "org.typelevel"     %% "cats-core"  % "1.0.1"
)
