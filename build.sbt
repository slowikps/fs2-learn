name := "fs2-learn"

version := "0.1"

scalaVersion := "2.12.3"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")

val fs2 = Seq(
  "co.fs2" %% "fs2-core" % "0.10.0-M7",
  "co.fs2" %% "fs2-io" % "0.10.0-M7"
)


libraryDependencies ++= fs2