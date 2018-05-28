lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-thread-pools",
    libraryDependencies += "io.monix" %% "monix" % "3.0.0-RC1"
  )
