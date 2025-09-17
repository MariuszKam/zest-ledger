package io.zestledger.platform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ActuatorHealthTest {

	@Test
	@DisplayName("Should return status Up when endpoint is called")
	void shouldReturnUpStatusWhenHealthEndpointIsCalled(@Autowired WebTestClient client) {
		client.get().uri("/actuator/health")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.valueOf("application/*+json"))
			.expectBody().jsonPath("status").isEqualTo("UP");

	}

}
