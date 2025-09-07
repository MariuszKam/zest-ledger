import info.solidsoft.gradle.pitest.PitestPluginExtension

plugins { id("info.solidsoft.pitest") }

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

extensions.configure<PitestPluginExtension> {
	junit5PluginVersion.set(libs.findVersion("pitJunit").get().toString())
	mutators.set(setOf("STRONGER"))
	timeoutConstInMillis.set(4000)
	timestampedReports.set(false)
	mutationThreshold.set(70)
	targetClasses.set(setOf("io.zestledger.*"))
	threads.set(4)
	outputFormats.set(setOf("HTML", "XML"))
	failWhenNoMutations.set(false)
}
