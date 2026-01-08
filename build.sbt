name := "play-cric"

version := "1.0.0"

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += javaJpa
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.24" % "provided"
libraryDependencies += "com.mysql" % "mysql-connector-j" % "9.5.0"


lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)