plugins {
	id("com.google.cloud.tools.jib")
}

jib {
	from {
		image = "eclipse-temurin:21-jre"
	}
	to {
		image = "zestledger/app:dev"
	}
	container {
		ports = listOf("8080")
		environment = mapOf(
			"JAVA_TOOL_OPTIONS" to "-XX:+UseContainerSupport"
		)
		creationTime = "USE_CURRENT_TIMESTAMP"
	}
}
