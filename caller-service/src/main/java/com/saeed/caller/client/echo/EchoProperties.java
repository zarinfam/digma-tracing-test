package com.saeed.caller.client.echo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clients.echo-service")
public record EchoProperties(String url) {
}
