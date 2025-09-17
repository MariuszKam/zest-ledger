plugins {
	id("org.sonarqube")
}

sonar {
	properties {
		property("sonar.organization", "mariuszkam")
		property("sonar.projectKey", "MariuszKam_zest-ledger")
		property("sonar.projectName", "zest-ledger")
		property("sonar.exclusions", "modules/architecture-tests/**")
		property("sonar.java.source", "21")
		property("sonar.coverage.exclusions", "**/*Application.java")
	}
}
