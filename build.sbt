name := "BlogTest"

version := "1.0"

scalaVersion := "2.11.8"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence" % "2.4.8",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.8",
  "com.typesafe.akka"  %% "akka-cluster-tools" % "2.4.8",
  "com.typesafe.akka" %% "akka-actor"      % "2.4.8"
)