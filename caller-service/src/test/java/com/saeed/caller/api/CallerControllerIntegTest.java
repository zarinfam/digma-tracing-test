package com.saeed.caller.api;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.net.ServerSocket;

import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CallerControllerIntegTest {
    private static int MOCK_SERVER_PORT;

    static {
        try (var serverSocket = new ServerSocket(0)) {
            MOCK_SERVER_PORT = serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void trackerProperties(DynamicPropertyRegistry registry) {
        String mockServerUrl = "http://localhost:" + MOCK_SERVER_PORT;
        registry.add("clients.decorate-service.url", () -> mockServerUrl);
        registry.add("clients.echo-service.url", () -> mockServerUrl);
    }

    private MockWebServer mockWebServer;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void beforeEach() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(MOCK_SERVER_PORT);
    }

    @AfterEach
    void afterEach() throws IOException {
        mockWebServer.shutdown();
    }



    @Test
    void buildApi_validRequest_buildDecoratedMessage() {
        var decorateJsonResponse = new MockResponse()
                .setResponseCode(OK.value())
                .setBody("**test**")
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(decorateJsonResponse);

        var echoJsonResponse = new MockResponse()
                .setResponseCode(OK.value())
                .setBody("""
                        	{
                        		"message": "Echo:**test**"
                        	}
                        """)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(echoJsonResponse);

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
