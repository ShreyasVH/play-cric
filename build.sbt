name := "play-cric"

version := "1.0.0"

scalaVersion := "3.8.2"

libraryDependencies += guice
libraryDependencies += evolutions
libraryDependencies += jdbc
libraryDependencies += javaJpa
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.44" % "provided"
libraryDependencies += "org.postgresql" % "postgresql" % "42.7.10"
libraryDependencies += "org.hibernate.orm" % "hibernate-core" % "7.2.7.Final"

Compile / javacOptions ++= Seq("-proc:full")

lazy val root = (project in file(".")).enablePlugins(PlayJava)