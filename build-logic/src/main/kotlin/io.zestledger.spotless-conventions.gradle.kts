plugins { id("com.diffplug.spotless") }

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

spotless {
	format("misc") {
		target("**/*.md", "**/*.yml", "**/*.yaml", "**/*.gradle", "**/*.kts", "**/*.txt", ".gitignore")
		trimTrailingWhitespace()
		endWithNewline()
	}

	java {
		googleJavaFormat(libs.findVersion("gjf").get().toString()).aosp()
		formatAnnotations()
		importOrder()
		removeUnusedImports()
	}
}
