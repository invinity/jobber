/*
 * This file was generated by the Gradle 'init' task.
 *
 * This settings file is used to specify which projects to include in your build-logic build.
 * This project uses @Incubating APIs which are subject to change.
 */

dependencyResolutionManagement {
    // Reuse version catalog from the main build.
    versionCatalogs {
        create("libs", { from(files("../gradle/libs.versions.toml")) })
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "build-logic"
