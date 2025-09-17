plugins {
	id("io.zestledger.arch-tests")
}

dependencies {
	testImplementation(project(":modules:domain"))
	testImplementation(project(":modules:application"))
	testImplementation(project(":modules:adapters-in:rest"))
	testImplementation(project(":modules:adapters-out:db"))
	testImplementation(project(":modules:platform"))
}
