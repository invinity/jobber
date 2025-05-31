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
    var testReportHtmlIndex = file("${reports.html.outputLocation.get()}/index.html").toURI()
    doLast {
        logger.lifecycle("Jacoco test code coverage report generated at: ${testReportHtmlIndex}")
        if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE))
            java.awt.Desktop.getDesktop()?.browse(testReportHtmlIndex)
    }
}