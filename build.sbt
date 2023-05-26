name := "play-error-handling"

version := "1.0.0"

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += javaJpa
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.24" % "provided"
libraryDependencies += "com.mysql" % "mysql-connector-j" % "8.0.31"


lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)