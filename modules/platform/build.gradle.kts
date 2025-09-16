plugins {
	id("io.zestledger.java-conventions")
	id("io.zestledger.spotless-conventions")
	id("io.zestledger.spring-boot-conventions")
}

dependencies {
	implementation(project(":modules:domain"))
	implementation(project(":modules:application"))
	implementation(platform(libs.spring.boot.bom))
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.boot.starter.actuator)
	testImplementation(libs.spring.boot.starter.test)
}
