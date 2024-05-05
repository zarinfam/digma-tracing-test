package com.saeed.caller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CallerServiceApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void buildApi_validRequest_buildDecoratedMessage() {
        webTestClient
                .get()
                .uri("/caller/build/test")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("Echo:**test**");
    }
}
