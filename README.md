## Simple skeleton for Scala on Android application with OpenCv 3.1

This is quick starter for Android application with computer vision in mind.

If you want to use it just:

1. Clone this repository
2. Rename root directory
3. Change application name in `app/src/res/strings.xml` > `app_name`
4. Change your package name in `AndroidManifest.xml`

If you want to launch application you need to install `sbt` and launch it, then switch to `app` project
and invoke `android:run` command.

```
$ sbt
$ project app
$ android:run
```

For more information about Scala on Android please visit: http://scala-android.org/
