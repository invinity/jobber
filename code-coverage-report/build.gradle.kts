plugins {
    id("base")
    id("jacoco-report-aggregation")
}

// rootProject.subprojects.forEach { p -> 
//     logger.warn("Adding Jacoco report aggregation for project: ${p.name}")
//     dependencies.jacocoAggregation(project(p.path))
// }

dependencies {
    jacocoAggregation(project(":controller"))
    jacocoAggregation(project(":domain"))
    jacocoAggregation(project(":usecases"))
    jacocoAggregation(project(":dynamodb-repository"))
    jacocoAggregation(project(":gcp-job-data-extractor"))
    jacocoAggregation(project(":util"))
    // jacocoAggregation(project(":springboot-app"))
}

reporting {
    reports {
        val testCodeCoverageReport by creating(JacocoCoverageReport::class) { 
            testSuiteName = "test"
        }
    }
}

tasks.check {
    dependsOn(tasks.named<JacocoReport>("testCodeCoverageReport")) 
}

tasks.named<JacocoReport>("testCodeCoverageReport") {
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
    doLast {
        logger.lifecycle("Jacoco test code coverage report generated at: ${reports.html.outputLocation.get()}")
        java.awt.Desktop.getDesktop()?.browse(file("${reports.html.outputLocation.get()}/index.html").toURI())
    }
}