# sbt-planout4j

A SBT plug-in which compiles [Planout4j](https://github.com/Glassdoor/planout4j) yaml files to [Planout language files](https://facebook.github.io/planout/docs/planout-language.html).
 
## Installation

Add the plugin to your SBT build.

    addSbtPlugin("me.rschatz" % "sbt-planout4j" % "0.0.1")
    
## Usage

Put your [Planout4j](https://github.com/Glassdoor/planout4j) namespace yaml files in `src/main/planout4j`. The location
can be altered by overwriting the `planout4jYamlSourceFolder` Setting in your build, i.e.

    planout4jYamlSourceFolder := new File("/tmp/in") 
 
The plugin will add a [Resource Generator](http://www.scala-sbt.org/0.13/docs/Howto-Generating-Files.html) to the build
and [Planout language](https://facebook.github.io/planout/docs/planout-language.html) files (json) will be generated as part
of the `managedResources` Task in `target/scala-2.10/resource_managed/main/planout`. The output location can be changed 
by overwriting the `planoutOutputFolder` Setting in your build, i.e.

    planout4jYamlSourceFolder := new File("/tmp/out")