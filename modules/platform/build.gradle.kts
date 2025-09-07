plugins {
	id("io.zestledger.java-conventions")
	id("io.zestledger.spotless-conventions")
}

dependencies {
	implementation(project(":modules:domain"))
	implementation(project(":modules:application"))
}
