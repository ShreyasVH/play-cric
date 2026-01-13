name := "play-cric"

version := "1.0.0"

scalaVersion := "2.13.18"

libraryDependencies += guice
libraryDependencies += evolutions
libraryDependencies += jdbc
libraryDependencies += javaJpa
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.42" % "provided"
libraryDependencies += "com.mysql" % "mysql-connector-j" % "9.5.0"
libraryDependencies += "org.hibernate.orm" % "hibernate-core" % "7.2.0.Final"

Compile / javacOptions ++= Seq("-proc:full")

lazy val root = (project in file(".")).enablePlugins(PlayJava)