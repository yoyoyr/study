package com.test.groovy

import com.test.groovy.extension.GradleExtensions
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.test.groovy.task.TestTask

public class PluginImpl implements Plugin<Project> {
    void apply(Project project) {
//        分析构建任务关系时调用
        println 'yoyo-'

        //构建任务  和任务依赖
        def preBuild = project.tasks.getByName("preBuild")
        def pluginTask = project.task('pluginTask') {
            group 'mygradle'
            doLast {
                println 'do plugin task'
            }

        }

        def create = project.tasks.create('createTask', TestTask)
//        指定了任务的依赖
        preBuild.dependsOn(create)
//        只指定了任务的执行顺序，运行preBuild不会导致pluginTask运行0
//        pluginTask.mustRunAfter(preBuild)

        //获取gradle的参数
        project.extensions.create("gradleExtensions", GradleExtensions)

        //任务关系解析完毕后，获取extension的值
        project.afterEvaluate {
            println 'gradle extensions age = ' + project.extensions.gradleExtensions.age
        }
    }
}