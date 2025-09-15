plugins {
	id("io.zestledger.java-conventions")
	id("io.zestledger.spotless-conventions")
	id("io.zestledger.quality-test")
}

dependencies {
	implementation(project(":modules:domain"))
	implementation(platform(libs.spring.boot.bom))
	implementation(libs.spring.boot.starter.data.jpa)
	implementation(libs.flyway.core)

	runtimeOnly(libs.flyway.postgresql)
	runtimeOnly(libs.postgresql)

	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.spring.boot.testcontainers)
	testImplementation(platform(libs.testcontainers.bom))
	testImplementation(libs.testcontainers.junit)
	testImplementation(libs.testcontainers.postgres)
}
