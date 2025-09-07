plugins { id("jacoco") }

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

jacoco {
	toolVersion = libs.findVersion("jacoco").get().toString()
}

tasks.withType(Test::class).configureEach {
	finalizedBy(tasks.named("jacocoTestReport"))
}

tasks.named<JacocoReport>("jacocoTestReport") {
	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.required.set(true)
	}
}

tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
	violationRules {
		rule { limit { minimum = "0.85".toBigDecimal() } }
	}
}

tasks.register("jacocoTest") {
	dependsOn("test", "jacocoTestReport", "jacocoTestCoverageVerification")
}
