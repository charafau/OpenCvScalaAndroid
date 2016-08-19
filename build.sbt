name := "OpenCvScala"
organization := "com.nullpointerbay.opencvscala"
version := "0.0.1"
versionCode := Some(1)

val sharedSettings = Seq(
    platformTarget := "android-21",
    minSdkVersion in Android := "21",
    showSdkProgress in Android := false,
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq("-feature", "-Xexperimental" ,"-language:implicitConversions", "-language:postfixOps", "-target:jvm-1.7"),
    javacOptions in Compile ++= "-source" :: "1.7" :: "-target" :: "1.7" :: Nil
   )

val openCVLibrary310 = project.settings(androidBuildAar:_*)
  .settings(sharedSettings: _*)
  .settings(libraryProject := true)

val app = project.androidBuildWith(openCVLibrary310)
  .settings(sharedSettings: _*)
  .settings(libraryProject := false)

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  "jcenter" at "http://jcenter.bintray.com"
)

useProguard in Android := false
useProguardInDebug in Android := (useProguard in Android).value
dexMulti in Android := true
dexMaxHeap in Android := "2048M"
