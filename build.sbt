/* basic project info */
name := "json-scala-perftest"

organization := "com.micro-workflow"

version := "0.1.0-SNAPSHOT"

description := "Performance tests for Scala JSON parsing options"

homepage := Some(url("https://github.com/polymorphic/json-scala-perftest"))

startYear := Some(2012)

licenses := Seq(
  ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))
)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/polymorphic/json-scala-perftest"),
    "scm:git:https://github.com/polymorphic/json-scala-perftest.git",
    Some("scm:git:git@github.com:polymorphic/json-scala-perftest.git")
  )
)

// organizationName := "My Company"

/* scala versions and options */
scalaVersion := "2.9.2"

// crossScalaVersions := Seq("2.9.1")

offline := false

scalacOptions ++= Seq("-deprecation", "-unchecked")

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* entry point */
mainClass in (Compile, packageBin) := Some("com.microWorkflow.jsonScalaPerftest.Main")

mainClass in (Compile, run) := Some("com.microWorkflow.jsonScalaPerftest.Main")

/* dependencies */
libraryDependencies ++= Seq (
    "com.persist" % "persist-json_2.9.2" % "0.9-RC1",
    "com.codahale" % "jerkson_2.9.1" % "0.5.0",
    "net.liftweb" % "lift-json_2.9.1" % "2.4",
    "net.minidev" % "json-smart" % "1.1.1",
    "com.rojoma" %% "rojoma-json" % "2.0.0",
    "io.spray" %% "spray-json" % "1.2.3" cross CrossVersion.full,
    "com.yammer.metrics" % "metrics-core" % "2.1.3",
    "fr.janalyse" %% "janalyse-jmx" % "0.5.0" % "compile"
  // "org.scalaz" %% "scalaz-core" % "7.0.0-M3",
  // "org.scalaz" %% "scalaz-effect" % "7.0.0-M3",
  // "org.scalacheck" %% "scalacheck" % "1.10.0" % "test"
)

/* you may need these repos */
resolvers ++= Seq(
  "spray" at "http://repo.spray.io",
  "coda" at "http://repo.codahale.com",
  "JAnalyse Repository" at "http://www.janalyse.fr/repository/"
  // Resolvers.sonatypeRepo("snapshots")
  // Resolvers.typesafeIvyRepo("snapshots")
  // Resolvers.typesafeIvyRepo("releases")
  // Resolvers.typesafeRepo("releases")
  // Resolvers.typesafeRepo("snapshots")
  // JavaNet2Repository,
  // JavaNet1Repository
)

/* sbt behavior */
logLevel in compile := Level.Warn

traceLevel := 5

/* publishing */
publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some(
    "snapshots" at nexus + "content/repositories/snapshots"
  )
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
                      }

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <developers>
    <developer>
      <id>polymorphic</id>
      <name>Dragos Manolescu</name>
      <email>coder@micro-workflow.com</email>
      <!-- <url></url> -->
    </developer>
  </developers>
)

// Josh Suereth's step-by-step guide to publishing on sonatype
// httpcom://www.scala-sbt.org/using_sonatype.html

/* assembly plugin */
mainClass in AssemblyKeys.assembly := Some("com.micro-workflow.jsonScalaPerftest.Main")

assemblySettings

test in AssemblyKeys.assembly := {}
