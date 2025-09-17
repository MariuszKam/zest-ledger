plugins { id("jacoco") }

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

jacoco {
	toolVersion = libs.findVersion("jacoco").get().toString()
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
		rule {
			limit {
				counter = "LINE"
				value = "COVEREDRATIO"
				minimum = "0.85".toBigDecimal()
			}
		}
	}
}

tasks.register("jacocoTest") {
	dependsOn("test", "jacocoTestReport", "jacocoTestCoverageVerification")
}
