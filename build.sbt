// build.sbt --- Scala build tool settings

scalaVersion := "2.11.5"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8", "-Ywarn-adapted-args", "-Ywarn-dead-code", "-Ywarn-numeric-widen", "-Ywarn-inaccessible")

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= List(
  "org.specs2" %% "specs2-core" % "2.4.15" % "test",
  "org.specs2" %% "specs2-matcher-extra" % "2.4.15" % "test"
)

testOptions in Test += Tests.Argument("-oD")

scalariformSettings
