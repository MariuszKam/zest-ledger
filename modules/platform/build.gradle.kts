plugins {
	id("io.zestledger.java-conventions")
	id("io.zestledger.spotless-conventions")
	id("io.zestledger.spring-boot-conventions")
}

dependencies {
	implementation(project(":modules:domain"))
	implementation(project(":modules:application"))
	implementation(project(":modules:adapters-in:rest"))
//	implementation(project(":modules:adapters-out:db")) Waiting to connect DataSource
	implementation(platform(libs.spring.boot.bom))
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.boot.starter.actuator)
	implementation(libs.spring.boot.starter.webflux)
	implementation(libs.logstash.logback.encoder)
	testImplementation(libs.spring.boot.starter.test)
}
