package com.saeed.caller.client.echo;

import com.saeed.caller.client.echo.model.Echo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EchoClient {

    private final EchoProperties properties;
    private final WebClient webClient;

    public EchoClient(EchoProperties properties, WebClient.Builder builder) {
        this.properties = properties;
        this.webClient = builder
                .baseUrl(properties.url())
                .build();
    }

    public Mono<Echo> echo(String message) {
        return webClient
                .get()
                .uri("/echo/" + message)
                .retrieve()
                .bodyToMono(Echo.class);
    }
}
