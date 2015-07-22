package me.rschatz

import java.io.FileReader

import com.glassdoor.planout4j.compiler.YAMLConfigParser
import com.google.common.io.Files
import org.json.simple.JSONValue
import sbt.Keys._
import sbt._

/**
 * A SBT plug-in which compiles [[https://github.com/Glassdoor/planout4j Planout4j]] yaml files to
 * [[https://facebook.github.io/planout/docs/planout-language.html Planout language files]].
 *
 * @author rschatz
 */
object Planout4jPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override def requires: Plugins = plugins.JvmPlugin

  object autoImport {
    val planout4jYamlSourceFolder = SettingKey[File](
      "planout4j-yaml-source-folder",
      "directory containing planout4j yaml files"
    )

    val planout4jYamlSources = SettingKey[Seq[File]](
      "planout4j-yaml-sources",
      "planout4j yaml source files to compile"
    )

    val planoutOutputFolder = SettingKey[File](
      "planout-output-folder",
      "output folder for generated planout language files (defaults to resourceManaged)"
    )

    val planout4jGen = TaskKey[Seq[File]](
      "planout4j-gen",
      "generate planout language files from planout4j yaml files using Plaout4j compiler"
    )
  }

  import autoImport._

  def planout4jSettings(conf: Configuration): Seq[Setting[_]] = inConfig(conf)(Seq(
    planout4jYamlSourceFolder <<= sourceDirectory { _ / "planout4j" },
    planout4jYamlSources <<= planout4jYamlSourceFolder { srcDir => (srcDir ** "*.yaml").get },
    planoutOutputFolder <<= resourceManaged { _ / "planout" },
    planout4jGen <<= (streams, planout4jYamlSources, planoutOutputFolder).map {
      (out, yamlFiles, outputDir) =>
        outputDir.mkdirs()

        if (yamlFiles.nonEmpty) {
          out.log.info(s"Generating planout language files for ${yamlFiles.mkString(", ")} ...")

          val namespaces = yamlFiles.map {
            yamlFile =>
              val namespace = Files.getNameWithoutExtension(yamlFile.getName)
              val reader = new FileReader(yamlFile)

              out.log.info(s"Parsing $namespace namespace")

              val parser = new YAMLConfigParser()
              val namespaceConfig = parser.parseAndValidate(reader, namespace)
              (namespace, JSONValue.toJSONString(namespaceConfig.getConfig))
          }.toMap

          namespaces.foreach {
            case (namespace, json) =>
              val jsonFile = new File(outputDir, s"$namespace.json")
              out.log.info(s"Writing $jsonFile")
              sbt.IO.write(jsonFile, json)
          }
        }
        (outputDir ** "*.json").get
    },
    resourceGenerators in Compile += planout4jGen.taskValue
  ))

  override def projectSettings = planout4jSettings(Compile)
}
