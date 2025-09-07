import org.owasp.dependencycheck.reporting.ReportGenerator

plugins {
	id("org.owasp.dependencycheck")
	id("org.cyclonedx.bom")
}

dependencyCheck {
	formats = listOf(
		ReportGenerator.Format.HTML.toString(),
		ReportGenerator.Format.XML.toString(),
		ReportGenerator.Format.JSON.toString()
	)
	failBuildOnCVSS = 9.0f
}

tasks.named("check") {
	dependsOn("dependencyCheckAnalyze", "cyclonedxBom")
}
