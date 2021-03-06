enablePlugins(ScalaJSPlugin)

enablePlugins(ScalaJSBundlerPlugin)

// @TODO[Security] Is this a good idea to leave this here long term?
// resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "com.raquo" %%% "airstream" % "0.10.1",
  "com.raquo" %%% "domtypes" % "0.10.1",
  "com.raquo" %%% "domtestutils" % "0.12.0" % Test,
  "org.scalatest" %%% "scalatest" % "3.1.1" % Test
)

scalacOptions ++= Seq("-deprecation", "-feature", "-language:higherKinds", "-language:implicitConversions")

version in installJsdom := "16.4.0"

useYarn := true

requireJsDomEnv in Test := true

parallelExecution in Test := false

scalaJSUseMainModuleInitializer := true

scalaJSLinkerConfig in (Compile, fastOptJS) ~= { _.withSourceMap(false) }
