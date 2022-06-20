ThisBuild / tlBaseVersion := "0.23"
ThisBuild / tlMimaPreviousVersions ++= (0 to 11).map(y => s"0.23.$y").toSet
ThisBuild / developers := List(
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

val Scala213 = "2.13.8"
ThisBuild / crossScalaVersions := Seq("2.12.16", Scala213, "3.1.2")
ThisBuild / scalaVersion := Scala213

lazy val root = project.in(file(".")).aggregate(prometheusMetrics).enablePlugins(NoPublishPlugin)

val http4sVersion = "0.23.12"
val prometheusVersion = "0.16.0"
val munitVersion = "0.7.29"
val munitCatsEffectVersion = "1.0.7"

lazy val prometheusMetrics = project
  .in(file("prometheus-metrics"))
  .settings(
    name := "http4s-prometheus-metrics",
    description := "Support for Prometheus Metrics",
    startYear := Some(2018),
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-core" % http4sVersion,
      "io.prometheus" % "simpleclient" % prometheusVersion,
      "io.prometheus" % "simpleclient_common" % prometheusVersion,
      "io.prometheus" % "simpleclient_hotspot" % prometheusVersion,
      "org.scalameta" %%% "munit-scalacheck" % munitVersion % Test,
      "org.typelevel" %%% "munit-cats-effect-3" % munitCatsEffectVersion % Test,
      "org.http4s" %%% "http4s-laws" % http4sVersion % Test,
      "org.http4s" %%% "http4s-server" % http4sVersion % Test,
      "org.http4s" %%% "http4s-client" % http4sVersion % Test,
      "org.http4s" %%% "http4s-dsl" % http4sVersion % Test,
    ),
  )

lazy val docs = project
  .in(file("site"))
  .dependsOn(prometheusMetrics)
  .settings(
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-server" % http4sVersion,
      "org.http4s" %%% "http4s-client" % http4sVersion,
      "org.http4s" %%% "http4s-dsl" % http4sVersion,
    )
  )
  .enablePlugins(Http4sOrgSitePlugin)