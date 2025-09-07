plugins {
	id("java")
}

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(libs.findVersion("jdk").get().toString().toInt())
	}
}

tasks.withType(Test::class).configureEach { useJUnitPlatform() }

dependencies {
	add("testImplementation", platform(libs.findLibrary("junit-bom").get()))
	add("testImplementation", libs.findLibrary("junit-jupiter").get())
	add("testRuntimeOnly", libs.findLibrary("junit-platform-launcher").get())
}
