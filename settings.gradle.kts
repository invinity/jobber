/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 * For more detailed information on multi-project builds, please refer to https://docs.gradle.org/8.11.1/userguide/multi_project_builds.html in the Gradle documentation.
 * This project uses @Incubating APIs which are subject to change.
 */

pluginManagement {
    // Include 'plugins build' to define convention plugins.
    includeBuild("build-logic")
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0-rc-1"
}

rootProject.name = "jobber"
include("controller", "domain", "usecases", "dynamodb-repository", "gcp-job-data-extractor", "util", "springboot-app", "code-coverage-report")

project(":controller").projectDir = file("adapter/interface/controller")
project(":dynamodb-repository").projectDir = file("adapter/repository/dynamodb")
project(":gcp-job-data-extractor").projectDir = file("adapter/pipeline/gcp-job-data-extractor")
project(":springboot-app").projectDir = file("application/springboot")
