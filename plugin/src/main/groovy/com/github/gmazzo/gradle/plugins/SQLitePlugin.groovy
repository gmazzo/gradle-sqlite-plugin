package com.github.gmazzo.gradle.plugins

import groovy.sql.Sql
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Plugin based on this Stackoverflow answer:
 * @see <a href="https://stackoverflow.com/a/33904851/1007772">https://stackoverflow.com/a/33904851/1007772</a>
 */
public class SQLitePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def extension = project.extensions.create('sqlite', SQLitePluginExtension)

        project.with {

            configurations {
                sqllite
            }

            repositories {
                jcenter()
            }

            afterEvaluate {
                dependencies {
                    sqllite extension.driverDependency
                }

                configurations.sqllite.each { File file ->
                    GroovyObject.class.classLoader.addURL(file.toURI().toURL())
                }
            }

            ext {
                openSQLiteDatabase = { File path ->
                    path.parentFile.mkdirs()
                    return Sql.newInstance("jdbc:sqlite:$path", "org.sqlite.JDBC")
                }
            }
        }
    }

}
