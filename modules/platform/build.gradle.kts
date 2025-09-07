plugins {
	id("io.zestledger.java-conventions")
	id("io.zestledger.jacoco-conventions")
	id("io.zestledger.pitest-conventions")
}

dependencies {
	implementation(project(":modules:domain"))
	implementation(project(":modules:application"))
}
