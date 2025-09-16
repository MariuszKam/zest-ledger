plugins {
	id("org.sonarqube")
}

sonar {
	properties {
		property("sonar.organization", "mariuszkam")
		property("sonar.projectKey", "mariuszkam_zest-ledger")
		property("sonar.projectName", "zest-ledger")
		property("sonar.exclusions", "modules/architecture-tests/**")
	}
}
