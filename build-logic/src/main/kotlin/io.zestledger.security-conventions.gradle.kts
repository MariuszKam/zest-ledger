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

	nvd {
		apiKey = System.getenv("NVD_API_KEY")
	}
}

val securityGate = tasks.register("securityGate") {
	group = "verification"
	description = "Runs OWASP Dependency-Check (aggregate) and generates CycloneDX SBOM"
	dependsOn("dependencyCheckAggregate", "cyclonedxBom")
}
