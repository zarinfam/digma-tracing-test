package com.saeed.caller.api;

import com.saeed.caller.client.decorate.DecorateClient;
import com.saeed.caller.client.decorate.model.Decorate;
import com.saeed.caller.client.echo.EchoClient;
import com.saeed.caller.client.echo.model.Echo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/caller")
public class CallerController {

    private final DecorateClient decorateClient;
    private final EchoClient echoClient;

    public CallerController(DecorateClient decorateClient, EchoClient echoClient) {
        this.decorateClient = decorateClient;
        this.echoClient = echoClient;
    }

    @GetMapping("/build/{message}")
    public Mono<String> build(@PathVariable String message) {
        return decorateClient.decorate(new Decorate(message, "**"))
                .flatMap(echoClient::echo)
                .map(Echo::message);
    }
}
