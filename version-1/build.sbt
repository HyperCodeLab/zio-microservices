ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

val zioVersion = "2.0.15"

lazy val root = (project in file("."))
  .settings(
    name := "ZioMicroServices",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    Test / fork := true
  )

libraryDependencies ++= Seq(
  "dev.zio"       %% "zio"            % zioVersion,
  "dev.zio"       %% "zio-json"       % "0.6.0",
  "dev.zio"       %% "zio-http"       % "3.0.0-RC2"
)

libraryDependencies ++= Seq(
  "dev.zio" %% "zio-test"          % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt"      % zioVersion % Test,
  "dev.zio" %% "zio-test-magnolia" % zioVersion % Test,
  "dev.zio" %% "zio-http-testkit" % "3.0.0-RC2" % Test
)

