package com.saeed.caller.client.decorate;

import com.saeed.caller.client.decorate.model.Decorate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DecorateClient {

    private final DecorateProperties properties;
    private final WebClient webClient;

    public DecorateClient(DecorateProperties properties, WebClient.Builder builder) {
        this.properties = properties;
        this.webClient = builder
                .baseUrl(properties.url())
                .build();
    }

    public Mono<String> decorate(Decorate decorate) {
        return webClient
                .post()
                .uri("/decorate")
                .bodyValue(decorate)
                .retrieve()
                .bodyToMono(String.class);
    }
}
