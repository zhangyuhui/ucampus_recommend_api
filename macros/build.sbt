name := "moviedemo-macro"

version := "1.0"

scalaVersion := "2.10.4"

organization := "com.moviedemo"

resolvers := Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/"
)

lazy val macros = (project in file(".")).enablePlugins(PlayScala)
