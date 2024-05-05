package com.saeed.caller;


import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

class CallerServiceEndToEndTests {

    @Test
    void buildApi_validRequest_buildDecoratedMessage() {
        WebTestClient.bindToServer().baseUrl("http://localhost:8080").build()
                .get()
                .uri("/caller/build/deli")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("Echo:**deli**");
    }
}
