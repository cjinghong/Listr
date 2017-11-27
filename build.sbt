name := "Listr"

version := "0.1"

scalaVersion := "2.12.3"

resolvers += "Local Maven Repository" at "file:///"+Path.userHome+ "/.ivy2/cache"

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
	"org.scalafx" % "scalafx_2.12" % "8.0.144-R12",
	"org.scalafx" % "scalafxml-core-sfx8_2.12" % "0.4",
	"org.scalikejdbc" %% "scalikejdbc"       % "3.1.0",
	"com.h2database"  %  "h2"                % "1.4.196",
	"ch.qos.logback"  %  "logback-classic"   % "1.2.3",
	"org.apache.derby" % "derby" % "10.14.1.0"
)

//mainClass in assembly := Some("hep88.Boom")

//EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE18)

//open program in different process
fork := true
