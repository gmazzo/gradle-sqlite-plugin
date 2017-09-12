package com.github.gmazzo.gradle.plugins

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.internal.DefaultBuildTask

import static org.gradle.testkit.runner.TaskOutcome.*

public class SQLitePluginTest extends GroovyTestCase {
    def projectDir = new File(System.getProperty('user.dir'), '/../sample')
    def pluginClasspathResource = getClass().classLoader.findResource("plugin-classpath.txt")
    def pluginClasspath = pluginClasspathResource.readLines().collect { new File(it) }
    def taskName = ':createSampleDatabase'
    def dbFile = new File("$projectDir/build/sample.db")

    private DefaultBuildTask run(boolean clean) {
        return GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath(pluginClasspath)
                .withArguments(clean ? ['clean', taskName] : taskName)
                .build()
                .task(taskName)
    }

    void testCreateSampleDatabase() {
        assertEquals(SUCCESS, run(true).getOutcome())
        assertTrue(dbFile.file)

        assertEquals(UP_TO_DATE, run(false).getOutcome())
        assertTrue(dbFile.file)
    }

}
