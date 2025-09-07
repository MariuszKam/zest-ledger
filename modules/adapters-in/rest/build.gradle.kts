plugins {
	id("io.zestledger.java-conventions")
	id("io.zestledger.spotless-conventions")
	id("io.zestledger.quality-test")
}

dependencies {
	implementation(project(":modules:domain"))
}
