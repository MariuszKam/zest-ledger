plugins { `kotlin-dsl` }

dependencies {
	implementation("com.diffplug.spotless:spotless-plugin-gradle:${libs.versions.spotless.get()}")
	implementation("info.solidsoft.gradle.pitest:gradle-pitest-plugin:${libs.versions.pitest.get()}")
	implementation("org.owasp.dependencycheck:org.owasp.dependencycheck.gradle.plugin:${libs.versions.owasp.get()}")
	implementation("org.cyclonedx.bom:org.cyclonedx.bom.gradle.plugin:${libs.versions.cyclonedx.get()}")
	implementation("org.springframework.boot:org.springframework.boot.gradle.plugin:${libs.versions.spring.boot.get()}")
	implementation("org.sonarqube:org.sonarqube.gradle.plugin:${libs.versions.sonar.qube.get()}")
}
