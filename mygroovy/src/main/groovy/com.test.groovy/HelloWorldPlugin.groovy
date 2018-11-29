package com.test.groovy;

import org.gradle.api.Plugin
import org.gradle.api.Project

public class HelloWorldPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        println 'apply--------------------'
        project.afterEvaluate {
            println 'afterEvaluate--------------------'
        }
        project.task('hello') << {
            println "hello yoyo"
        }
    }
}
