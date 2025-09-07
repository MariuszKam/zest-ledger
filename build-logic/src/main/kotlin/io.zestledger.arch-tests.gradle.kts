import gradle.kotlin.dsl.accessors._2c95f20277cbe6143532f6e8d67e36cc.testImplementation

plugins {
	id("io.zestledger.java-conventions")
	id("io.zestledger.spotless-conventions")
}

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

dependencies {
	testImplementation(libs.findLibrary("archunit-junit5").get())
}

tasks.withType(Test::class).configureEach {
	testLogging {
		events("FAILED", "SKIPPED", "STANDARD_ERROR")
		exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
		showStackTraces = true
		showExceptions = true
	}
}
