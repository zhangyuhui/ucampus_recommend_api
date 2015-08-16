name := "ucampus-api"

version := "1.1"

scalaVersion := "2.10.4"

organization := "com.ucampus"

resolvers := Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  anorm,
  ws,
  javaCore,
  javaWs,
  "commons-codec" % "commons-codec" % "1.6",
  "com.chuusai" % "shapeless" % "2.0.0" cross CrossVersion.full,
  "com.tzavellas" % "sse-guice" % "0.7.1",
  "com.typesafe.play" %% "play-slick" % "0.5.0.8",
  "com.typesafe.play" %% "play-test" % "2.3.4" % "compile",
  "com.wordnik" %% "swagger-play2" % "1.3.10",
  "commons-configuration" % "commons-configuration" % "1.9",
  "commons-validator" % "commons-validator" % "1.4.0",
  "mysql" % "mysql-connector-java" % "5.1.32",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.mongodb" %% "casbah" % "2.5.0",
  "org.elasticsearch" % "elasticsearch" % "0.20.0"
)

lazy val macros = RootProject(file("macros"))

lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(macros).aggregate(macros)