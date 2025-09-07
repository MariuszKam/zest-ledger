rootProject.name = "zestledger"

include(
	":modules:domain",
	":modules:application",
	":modules:adapters-in:rest",
	":modules:adapters-out:db",
	":modules:platform",
	":modules:architecture-tests"
)

pluginManagement {
	includeBuild("build-logic")
	repositories {
		gradlePluginPortal()
		mavenCentral()
	}
}

dependencyResolutionManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
	}
}
