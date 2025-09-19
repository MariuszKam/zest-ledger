package io.zestledger.platform.security;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class SecurityHeadersE2ETest {

    @Autowired WebTestClient client;

    @Test
    @DisplayName("GET /actuator/health -> baseline security headers incl. CSP")
    void actuatorHealthShouldContainSecurityHeaders() {
        client.get()
                .uri("/actuator/health")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .valueEquals("Content-Security-Policy", "default-src 'none'")
                .expectHeader()
                .valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader()
                .valueEquals("X-Frame-Options", "DENY")
                .expectHeader()
                .valueEquals("Referrer-Policy", "no-referrer");
    }

    @Test
    @DisplayName("GET /actuator/info -> CSP present")
    void actuatorInfoShouldContainCsp() {
        client.get()
                .uri("/actuator/info")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .valueEquals("Content-Security-Policy", "default-src 'none'");
    }

    @Test
    @DisplayName("GET /no-such -> 404 still contains CSP")
    void notFoundShouldContainCsp() {
        client.get()
                .uri("/no-such")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectHeader()
                .valueEquals("Content-Security-Policy", "default-src 'none'");
    }

    @Test
    @DisplayName("HEAD /actuator/health -> CSP present")
    void headShouldContainCsp() {
        client.method(HttpMethod.HEAD)
                .uri("/actuator/health")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .valueEquals("Content-Security-Policy", "default-src 'none'");
    }

    @Test
    @DisplayName("OPTIONS /actuator/health -> CSP present")
    void optionsShouldContainCsp() {
        client.method(HttpMethod.OPTIONS)
                .uri("/actuator/health")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .valueEquals("Content-Security-Policy", "default-src 'none'");
    }
}
