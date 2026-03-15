name := "play-cric"

version := "1.0.0"

scalaVersion := "3.8.2"

libraryDependencies += guice
libraryDependencies += evolutions
libraryDependencies += jdbc
libraryDependencies += javaJpa
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.42" % "provided"
libraryDependencies += "com.mysql" % "mysql-connector-j" % "9.6.0"
libraryDependencies += "org.hibernate.orm" % "hibernate-core" % "7.2.4.Final"

Compile / javacOptions ++= Seq("-proc:full")

lazy val root = (project in file(".")).enablePlugins(PlayJava)