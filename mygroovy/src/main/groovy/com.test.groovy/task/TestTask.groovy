package com.test.groovy.task;

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class TestTask extends DefaultTask {


    public TestTask() {
        group = 'mygradle'
    }

    @TaskAction
    void doTask() {

        println 'gradle extensions name = ' + project.extensions.gradleExtensions.name

        def assetPath = "/src/main/assets/www"
        def index = project.file(new File(assetPath + "/sharelibs.index"))
        def contents = ""
        def tree = project.fileTree(dir: "${assetPath}", exclude: ['**/.svn/**', '*.index'])
        println "asset path = ${assetPath}"

        tree.visit { fileDetails ->
            if (!fileDetails.isDirectory()) {
                contents += "${fileDetails.relativePath}" + "\n"
            }
        }

        index.write contents

    }
}
