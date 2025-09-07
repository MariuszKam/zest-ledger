plugins { `kotlin-dsl` }

dependencies {
	implementation("com.diffplug.spotless:spotless-plugin-gradle:${libs.versions.spotless.get()}")
	implementation("info.solidsoft.gradle.pitest:gradle-pitest-plugin:${libs.versions.pitest.get()}")
}
