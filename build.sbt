name := "play-cric"

version := "1.0.0"

scalaVersion := "3.8.4"

libraryDependencies += guice
libraryDependencies += evolutions
libraryDependencies += jdbc
libraryDependencies += javaJpa
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.46" % "provided"
libraryDependencies += "org.postgresql" % "postgresql" % "42.7.11"
libraryDependencies += "org.hibernate.orm" % "hibernate-core" % "7.3.4.Final"

Compile / javacOptions ++= Seq("-proc:full")

lazy val root = (project in file(".")).enablePlugins(PlayJava)